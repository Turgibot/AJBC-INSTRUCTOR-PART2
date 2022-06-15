package ajbc.learn.mongodb.crud;

import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import ajbc.learn.nosql.MyConnectionString;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import static java.util.Arrays.asList;

public class Basics {
	private static final int MAX_GRADE = 100;
	private static final int CLASS_ID_MAX = 1;
	private static final int STUDENT_ID_MAX = 10000;
	
	private static final Random rand = new Random();

	public static void main(String[] args) {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(MyConnectionString.uri()))
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();

		try (MongoClient mongoClient = MongoClients.create(settings)) {

			// display databases names
			mongoClient.listDatabaseNames().forEach(System.out::println);

			// create a database

			MongoDatabase myDB = mongoClient.getDatabase("my_own_db");
			System.out.println("\n------------and after I created a new DB -------------\n");
			mongoClient.listDatabaseNames().forEach(System.out::println);

			// We must first add a collection to the DB to see it
			System.out.println("\n------------and after I created a new Collection -------------\n");
			MongoCollection<Document> myCollection = myDB.getCollection("myCollection");
			mongoClient.listDatabaseNames().forEach(System.out::println);

			// lets add a document
			myCollection.insertOne(generateNewStudent(STUDENT_ID_MAX, CLASS_ID_MAX, "Davis", "Samson"));
			System.out.println("data inserted for studentId 10000.");

			System.out.println("\n------------and after I inserted a doc -------------\n");
			mongoClient.listDatabaseNames().forEach(System.out::println);
		}

	}

	private static Document generateNewStudent(int studentId, int classId, String firstName, String lastName) {
		List<Document> scores = asList(new Document("english", rand.nextInt(MAX_GRADE + 1)),
				new Document("mathematics", rand.nextInt(MAX_GRADE + 1)),
				new Document("french", rand.nextInt(MAX_GRADE + 1)),
				new Document("advanced_java", rand.nextInt(MAX_GRADE + 1) ));

		return new Document("_id", new ObjectId()).append("student_id", studentId).append("first_name", firstName)
				.append("last_name", lastName).append("class_id", classId).append("scores", scores);
	}

	private static Document generateNewGrade(double studentId, double classId) {
		List<Document> scores = asList(new Document("type", "exam").append("score", rand.nextDouble() * 100),
				new Document("type", "quiz").append("score", rand.nextDouble() * 100),
				new Document("type", "homework").append("score", rand.nextDouble() * 100),
				new Document("type", "homework").append("score", rand.nextDouble() * 100));
		return new Document("_id", new ObjectId()).append("student_id", studentId).append("class_id", classId)
				.append("scores", scores);
	}
}
