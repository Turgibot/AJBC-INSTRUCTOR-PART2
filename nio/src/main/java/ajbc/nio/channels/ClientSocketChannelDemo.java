package ajbc.nio.channels;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientSocketChannelDemo {

	private static final String HOST = "localhost";
	private static final int PORT = 9090, BUFFER_SIZE = 256;

	public static void main(String[] args) throws IOException {
		blocking();
	}

	private static void blocking() throws IOException {
		
		String text = "This is the client talking";
		
		SocketChannel clientSocket = SocketChannel.open();
		clientSocket.connect(new InetSocketAddress(HOST, PORT));
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		
		buffer.put(text.getBytes());
		buffer.flip();
		while(buffer.hasRemaining()) {
			clientSocket.write(buffer);
		}
		buffer.clear();
		
		System.out.println("Client sent to server: "+text);
		System.out.println("------------------------------");
		System.out.println("Client received: ");
		
		while (clientSocket.read(buffer)!=-1) {
			buffer.flip();
			while(buffer.hasRemaining()) {
				System.out.print((char)buffer.get());
			}
			buffer.clear();
		}
		
		
		
	}
}
