package ajbc.nio.channels;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BasicChannel1 {

	public static void run() throws IOException {

		String fileName = "data/nio-data.txt";
		// read from filechannel to a buffer
		try (FileInputStream fileInputStream = new FileInputStream(fileName);
				FileChannel fileChannel = fileInputStream.getChannel();
				WritableByteChannel outChannel = Channels.newChannel(System.out)) {
			MappedByteBuffer buffer = fileChannel.map(MapMode.READ_ONLY, 0, fileChannel.size());

			// write to channel from a buffer
			while (buffer.hasRemaining()) {
				outChannel.write(buffer);
			}
		}

//		
//		String text = "";
//		while (buffer.hasRemaining()) {
//			char ch = (char)buffer.get();
//			text += ch;
//		}
//		System.out.println(text);
	}

}
