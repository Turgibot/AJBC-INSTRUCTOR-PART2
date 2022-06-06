package ajbc.nio.channels;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Runner {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
//		BasicChannel1.run();
//		BasicChannel2.run();
//		BufferDemo.run();
//		PathDemo.run();
//		FilesDemo.run();
		AsyncFileDemo.run();

	}

}
