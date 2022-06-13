package ajbc.nio.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorServerDemo {

	private static final int PORT = 9090, BUFFER_SIZE = 256;

	public static void main(String[] args) throws IOException, InterruptedException {
		Selector selector = Selector.open();

		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.socket().bind(new InetSocketAddress(PORT));
		serverChannel.configureBlocking(false);

		serverChannel.register(selector, SelectionKey.OP_ACCEPT);

		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		
		while(true) {
//			selector.select(); //blocking
			while(selector.selectNow()==0);//non-blocking
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectedKeys.iterator();
			while(iter.hasNext()) {
				
				SelectionKey key = iter.next();
				
				if(key.isAcceptable()) {
					registerServer(selector, serverChannel);
					System.out.println("client connected");
				}
				else
					if(key.isReadable()) {
						echo(buffer, key);
					}
				iter.remove();
			}
		}

	}

	private static void echo(ByteBuffer buffer, SelectionKey key) throws IOException, InterruptedException {
		SocketChannel client = (SocketChannel) key.channel();
		
		client.read(buffer);
		System.out.println("Data read");
		process();
		buffer.flip();
		client.write(buffer);
		buffer.clear();
		System.out.println("Data sent");
	}

	private static void registerServer(Selector selector, ServerSocketChannel serverChannel) throws IOException {
		SocketChannel client = serverChannel.accept();
		client.configureBlocking(false);
		client.register(selector, SelectionKey.OP_READ);
	}

	private static void process() throws InterruptedException {
//		System.out.println(" ... PROCESSING ...");
//		Thread.sleep(500);
//		System.out.println(".");
//		Thread.sleep(500);
//		System.out.println(".");
//		Thread.sleep(500);
//		System.out.println(".");
//		Thread.sleep(500);
//		System.out.println(".");
//		Thread.sleep(500);
//		System.out.println("----- FINISHED -----");
		
	}
}
