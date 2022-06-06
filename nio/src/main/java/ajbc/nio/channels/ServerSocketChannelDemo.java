package ajbc.nio.channels;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo {

	private static final int BUFFER_SIZE = 256;
	private static final int PORT = 9090;

	public static void run() throws IOException {
		nonblocking();
	}

	private static void nonblocking() throws IOException {

		ServerSocketChannel serverSocket = ServerSocketChannel.open();
		serverSocket.configureBlocking(false);
		
		serverSocket.bind(new InetSocketAddress(PORT));
		String text = "";
		while (true) {
			SocketChannel socketChannel = serverSocket.accept();
			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
			while (buffer.hasRemaining() && socketChannel.read(buffer) != -1) {
				socketChannel.write(buffer);
			}
			buffer.flip();
			System.out.println("\nServer received:");
			while (buffer.hasRemaining()) {
				text += (char) buffer.get();
			}
			System.out.println(text.trim());
			
			buffer.clear();
			text = "";
		}

		// serverSocket.close();
	}
}
