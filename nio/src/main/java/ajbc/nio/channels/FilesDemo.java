package ajbc.nio.channels;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;

public class FilesDemo {
	public static void run() {
		Path path = Paths.get("data/logging.properties");

		boolean pathExists = Files.exists(path, new LinkOption[] { LinkOption.NOFOLLOW_LINKS }); // should not follow
																									// symbolic links in
																									// the file system
																									// to determine if
																									// the path exists
		System.out.println(pathExists);

		// create directory
		Path path1 = Paths.get("./data/subdir");

		try {
			Path newDir = Files.createDirectory(path1);
			System.out.println("The directory got created.");
		} catch (FileAlreadyExistsException e) {
			System.out.println("The directory already exists.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// copy files

		Path sourcePath = Paths.get("data/nio-data.txt");
		Path destinationPath = Paths.get("data/nio-data-copy.txt");

		try {
			Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("The copy got created.");
		} catch (FileAlreadyExistsException e) {
			System.out.println("The copy already exists.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// move files
		Path sourcePath2 = Paths.get("data/nio-data-copy.txt");
		Path destinationPath2 = Paths.get("data/subdir/nio-data-moved.txt");

		try {
			Files.move(sourcePath2, destinationPath2, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("The file got moved.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		// delete
		Path path3 = Paths.get("data/subdir/nio-data-moved.txt");

		try {
			Files.delete(path3);
		} catch (IOException e) {
			System.out.println("The file deletion failed.");
		}
	}
}
