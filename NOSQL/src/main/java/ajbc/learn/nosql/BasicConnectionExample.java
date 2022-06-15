package ajbc.learn.nosql;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class BasicConnectionExample {

	public static void main(String[] args) {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(MyConnectionString.uri()))
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();

		try (MongoClient mongoClient = MongoClients.create(settings)) {

			MongoDatabase database = mongoClient.getDatabase("sample_mflix");

			FindIterable<Document> docs = database.getCollection("movies").find();
			int num = 100;
			int counter = 0;
			for (Document document : docs) {
				if (num == counter) {

					System.out.println("-----------------------------------------------------------------");
					System.out.println(document.toJson());
					System.out.println("-----------------------------------------------------------------");
					break;
				}
				counter++;
			}
		}
	}

}
