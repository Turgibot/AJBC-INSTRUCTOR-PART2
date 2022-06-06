package ajbc.nio.channels;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo {

	private static final int BUFFER_SIZE = 50;

	public static void run() throws IOException {

		// create a buffer using allocate
		CharBuffer buffer = CharBuffer.allocate(BUFFER_SIZE);
		// write directly to buffer

		buffer.put('J');
		buffer.put('A');
		buffer.put('V');
		buffer.put('A');

		buffer.append(" is the best!!!");

//		buffer.put(0, 'N');

		// to read we first nee to flip

		buffer.flip();

		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		System.out.println();

		// position is now at the same location as limit - use rewind to read again

		buffer.rewind();

		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		System.out.println();


		
		// read part of the buffer and continue writing after the unread part - use
		// compact
		buffer.rewind();
		buffer.get();
		buffer.get();
		buffer.get();
		buffer.get();
		// we just read 4 chars - to delete them use compact
		buffer.compact();

		buffer.put(" And continue...........");

		buffer.flip();

		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		System.out.println();
		buffer.clear();
		
		//
		buffer.put("This is a new text");
		buffer.flip();
		int counter = 0;
		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
			if(counter == 4)
				buffer.mark();
			counter++;
		}
		System.out.println();
		// position is moved to the marked position
		buffer.reset();
		
		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		
	}

}
