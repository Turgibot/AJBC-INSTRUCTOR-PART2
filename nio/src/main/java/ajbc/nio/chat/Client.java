package ajbc.nio.chat;

import java.io.IOException;
import java.lang.Thread.State;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class Client {

	private static final String STOP = "STOP";
	private static final int PORT = 9090, BUFFER_SIZE = 256;

	public static void main(String[] args) throws IOException {

		Selector selector = Selector.open();
		SocketChannel clientSocket = SocketChannel.open();
		clientSocket.connect(new InetSocketAddress("localhost", PORT));
		clientSocket.configureBlocking(false);

		clientSocket.register(selector, SelectionKey.OP_READ);
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

		Thread asyncWriteThread = new Thread(new NonBlockWrite(clientSocket, selector));
		asyncWriteThread.start();

		while (asyncWriteThread.getState() == State.RUNNABLE) {

			selector.select(); // this blocks until a ready channel registers
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectedKeys.iterator();

			while (iter.hasNext()) {

				SelectionKey key = iter.next();

				if (key.isWritable()) {

					send(buffer, key);
				}

				else if (key.isReadable()) {
					read(buffer, key);
				}

				iter.remove();
			}
		}
		
	}

	private synchronized static void send(ByteBuffer buffer, SelectionKey key) throws IOException {

		SocketChannel client = (SocketChannel) key.channel();
		String txt = (String) key.attachment();
		if(txt==null)
			return;
		buffer.clear();
		buffer.put(txt.getBytes());
		buffer.flip();
		client.write(buffer);
		buffer.clear();
		client.register(key.selector(), SelectionKey.OP_READ);
	}

	private static void read(ByteBuffer buffer, SelectionKey key) throws IOException {

		SocketChannel client = (SocketChannel) key.channel();
		client.read(buffer);
		buffer.flip();
		String txt = "";

		while (buffer.hasRemaining())
			txt += (char) buffer.get();

		buffer.clear();
		System.out.println("------------------ CHAT ------------------");
		System.out.println(txt.trim());
		System.out.println("-------------------------------------------");
		client.register(key.selector(), SelectionKey.OP_READ);
	}

	static class NonBlockWrite implements Runnable {
		SocketChannel clientChannel;
		Selector selector;
		Scanner sc;
		ByteBuffer buffer;
		public NonBlockWrite(SocketChannel clientChannel, Selector selector) {
			this.clientChannel = clientChannel;
			this.selector = selector;
			sc = new Scanner(System.in);
			buffer = ByteBuffer.allocate(BUFFER_SIZE); 
		}

		@Override
		public void run() {
			System.out.println("write msg to send to chat:");
			while (true) {
				try {
					String txt = sc.nextLine();
					if (txt.equals(STOP)) {
						break;
					}
					buffer.clear();
					buffer.put(txt.getBytes());
					buffer.flip();
					clientChannel.write(buffer);
					buffer.clear();
					System.out.println("-----------------You Said------------------");
					System.out.println(txt);
					System.out.println("-------------------------------------------");
				} catch (ClosedChannelException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				clientChannel.close();
				selector.close();
				sc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
