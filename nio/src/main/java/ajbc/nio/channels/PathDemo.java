package ajbc.nio.channels;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemo {
	public static void run() {
		Path path = Paths.get("c:\\data\\myfile.txt");
		System.out.println(path);
		System.out.println("absolute: " + path.isAbsolute());

		// creating a relative path
		Path projects = Paths.get("d:\\data", "projects");
		System.out.println(projects);
		System.out.println("absolute: " + projects.isAbsolute());

		Path currentDir = Paths.get(".");
		System.out.println("current directory " + currentDir);
		System.out.println("current directory (absolute)" + currentDir.toAbsolutePath());

		// relativize -
		Path basePath = Paths.get("/data");
		Path filePath = Paths.get("/data/subdata/subsubdata/myfile.txt");

		Path basePathToFile = basePath.relativize(filePath);
		Path fileToBasePath = filePath.relativize(basePath);

		System.out.println(basePathToFile);
		System.out.println(fileToBasePath);

		// notmailze path
		String originalPath = "d:\\data\\projects\\a-project\\..\\another-project";

		Path path1 = Paths.get(originalPath);
		System.out.println("path1 = " + path1);

		Path path2 = path1.normalize();
		System.out.println("path2 = " + path2);
		
		//is this path real
		Path path3 = Paths.get("c:\\data\\myfile.txt");
		boolean isReal = false;
		try {
			Path realPath = path3.toRealPath();
			isReal = true;
		} catch (IOException ignore) {}
		
		System.out.println("is it a real path ? "+isReal);
		
		Path path4 = Paths.get(".\\pom.xml");
		try {
			Path realPath = path4.toRealPath();
			isReal = true;
		} catch (IOException ignore) {}
		
		System.out.println("is it a real path ? "+isReal);
	}
}
