package ajbc.learn.nosql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.mongodb.ConnectionString;

public class MyConnectionString {
	private static final String PROPERTIES_FILE = "nosql.properties";

	public static ConnectionString uri() {
		FileInputStream fileInputStream = null;

		try {
			Properties props = new Properties();
			fileInputStream = new FileInputStream(PROPERTIES_FILE);
			props.load(fileInputStream);

			String user = props.getProperty("user");
			String password = props.getProperty("password");
			String cluster = props.getProperty("cluster_name");
			String param1 = props.getProperty("param1");
			String param2 = props.getProperty("param2");
			String serverName = props.getProperty("server_name");
			
			String uri = 	"mongodb+srv://"+user+":"+password+"@"+cluster+"."+serverName+"/?"+param1+"&"+param2;
			return new ConnectionString(uri);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
		return null;
	}
}
