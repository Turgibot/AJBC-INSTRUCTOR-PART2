package ajbc.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Server {

	private static final int PORT = 9090, BUFFER_SIZE = 256;

	public static void main(String[] args) throws IOException, InterruptedException {
		Selector selector = Selector.open();
		List<SocketChannel> clients = new ArrayList<>();
		ServerSocketChannel serverChannel = ServerSocketChannel.open();
		serverChannel.socket().bind(new InetSocketAddress(PORT));
		serverChannel.configureBlocking(false);

		serverChannel.register(selector, SelectionKey.OP_ACCEPT);

		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

		while (true) {
			selector.select(); //blocking
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectedKeys.iterator();
			while (iter.hasNext()) {

				SelectionKey key = iter.next();

				if (key.isAcceptable()) {
					registerServer(selector, serverChannel, clients);
					System.out.println("client %d connected".formatted(clients.size()));
				} else if (key.isReadable()) {
					echo(buffer, key, clients);
				}
				iter.remove();
			}
		}

	}

	private static void echo(ByteBuffer buffer, SelectionKey key, List<SocketChannel> clients)
			throws IOException, InterruptedException {
		SocketChannel clientChannel = (SocketChannel) key.channel();
		int channelNum = (int) key.attachment();
		clientChannel.read(buffer);
		buffer.flip();
		for (SelectionKey key1 : key.selector().keys()) {
			if (key1.isValid() && key1.channel() instanceof SocketChannel) {
				SocketChannel sch = (SocketChannel) key1.channel();
				int schNum = (int) key1.attachment();
				if (schNum != channelNum) {
					sch.write(buffer);
					buffer.rewind();
				}
			}
		}
		buffer.clear();

	}

	private static void registerServer(Selector selector, ServerSocketChannel serverChannel,
			List<SocketChannel> clients) throws IOException {
		SocketChannel client = serverChannel.accept();
		clients.add(client);
		client.configureBlocking(false);
		client.register(selector, SelectionKey.OP_READ, clients.size());
	}
}
