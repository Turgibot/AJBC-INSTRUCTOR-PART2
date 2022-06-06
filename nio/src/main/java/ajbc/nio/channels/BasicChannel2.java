package ajbc.nio.channels;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BasicChannel2 {

	private static final int BUFFER_SIZE = 10;

	public static void run() throws IOException {

		String directory = "data";
		String fileName = "nio-data.txt";
		
		//create a channel using a path
		Path path = Paths.get(directory , fileName);
//		String name = path.getFileName().toString();
		// access a file 
		RandomAccessFile file = new RandomAccessFile(path.toString(), "rw");
		//get the file's channel
		FileChannel fileChannel = file.getChannel();
		//create a buffer to hold and manipulate the file's data
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		
		//read text from filechannel into the buffer
		int bytesRead = 0;
		bytesRead = fileChannel.read(buffer);
		while(bytesRead!= -1){
			
			buffer.flip();
			while(buffer.hasRemaining())
				System.out.print((char)buffer.get());
			buffer.clear();
			bytesRead = fileChannel.read(buffer);
			System.out.print(" | ");
		}
		
	}

}
