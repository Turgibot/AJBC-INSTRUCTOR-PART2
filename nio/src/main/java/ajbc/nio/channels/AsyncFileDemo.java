package ajbc.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncFileDemo {
	private static final int NUM_BYTES = 1024;

	public static void run() throws IOException, InterruptedException, ExecutionException {

		//reading using CompletionHandler
		
		//reading using Future 
		Path path1 = Paths.get("data", "shakespeare.txt");
		AsynchronousFileChannel fileChannel1 = AsynchronousFileChannel.open(path1, StandardOpenOption.READ);
		ByteBuffer buffer1 = ByteBuffer.allocate((int)fileChannel1.size());
		long start = System.currentTimeMillis();
		Future<Integer> numBytes = fileChannel1.read(buffer1, 0);
		
//		while(!numBytes.isDone())
//			System.out.println("Reading....");
		
		//here we can do something else
		
		numBytes.get();
		long end = System.currentTimeMillis();
		System.out.println("Reading took %d [ms] (forever)".formatted(end-start));
		buffer1.flip();
		byte[] data = new byte[buffer1.limit()];
		buffer1.get(data);
//		System.out.println(new String(data));
		buffer1.clear();
		//writing using CompletionHandler
		
		Path path = Paths.get("data", "test-write.txt");
		
		if(!Files.exists(path))
			Files.createFile(path);
		
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
		ByteBuffer buffer = ByteBuffer.allocate(NUM_BYTES);
		
		String text = new String("And I in going, madam, weep o'er my father's death\r\n"
				+ "anew: but I must attend his majesty's command, to\r\n"
				+ "whom I am now in ward, evermore in subjection.\r\n"
				+ "LAFEU\r\n"
				+ "You shall find of the king a husband, madam; you,\r\n"
				+ "sir, a father: he that so generally is at all times\r\n"
				+ "good must of necessity hold his virtue to you; whose\r\n"
				+ "worthiness would stir it up where it wanted rather\r\n"
				+ "than lack it where there is such abundance.\r\n"
				+ "COUNTESS");
		
		
		buffer.put(text.getBytes());
		buffer.flip();
		Double attachment = new Double(4);
		
		CompletionHandler<Integer, Double> completionHandler = new CompletionHandler<Integer, Double>(){

			@Override
			public void completed(Integer result, Double attachment) {
				System.out.println("result " +result);
				System.out.println("attachment "+ attachment);
			}

			@Override
			public void failed(Throwable exc, Double attachment) {
				System.out.println(exc.getMessage());
				
			}};

			
		fileChannel.write(buffer,0, attachment, completionHandler);
		
		
		Thread.sleep(1000);
		
		
		
		
		
		
		
		
		
		//writing using Future		
		
		
		
		
		
		
		
		
		
		
		
		

		
		
		fileChannel1.close();
		fileChannel.close();
		
		
		
		
	}
}
