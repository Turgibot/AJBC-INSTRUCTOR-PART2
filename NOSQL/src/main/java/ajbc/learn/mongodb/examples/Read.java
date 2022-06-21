package ajbc.learn.mongodb.crud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import ajbc.learn.mongodb.models.Student;
import ajbc.learn.nosql.MyConnectionString;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;


public class Read {
	private static final int CLASS_ID_MAX = 1;
	private static final int STUDENT_ID_MAX = 10000;
	private static final int MAX_GRADE = 100;
	private static final Random rand = new Random();

	public static void main(String[] args) {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(MyConnectionString.uri()))
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
		JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
		try (MongoClient mongoClient = MongoClients.create(settings)) {

			MongoDatabase myDB = mongoClient.getDatabase("my_own_db");
			MongoCollection<Document> myCollection = myDB.getCollection("myCollection");

			Document student1 = myCollection.find(new Document("first_name", "Guy")).first();
			System.out.println("Student 1: " + student1.toJson(prettyPrint));

			//Using filters
			Document student2 = myCollection.find(eq("last_name", "Tordjman")).first();
			System.out.println("Student 2: " + student2.toJson(prettyPrint));
			
			//getting a Student object
			
			Gson gson = new Gson();
			Student stud = gson.fromJson(student2.toJson(), Student.class);
			System.out.println(stud);
			// multiple documents returned
			
			FindIterable<Document> iterable = myCollection.find(gte("student_id", STUDENT_ID_MAX/1.5d));
			MongoCursor<Document> cursor = iterable.iterator();
			System.out.println("Student list with cursor: ");
			while (cursor.hasNext()) {
			    System.out.println(cursor.next().toJson(prettyPrint));
			}
			
			List<Document> studentList = myCollection.find(gte("student_id", STUDENT_ID_MAX/1.5d)).into(new ArrayList<>());
			System.out.println("Student list with an ArrayList:");
			studentList.forEach(x->System.out.println(x.toJson(prettyPrint)));
			
			List<Integer> ids = Arrays.asList(10000,2,6460);
			System.out.println(" ----------------- IN ------------------");
			studentList = myCollection.find(in("student_id", ids)).into(new ArrayList<>());
			studentList.forEach(x->System.out.println(x.toJson(prettyPrint)));
			
			System.out.println(" ----------------- AND ------------------");
			studentList = myCollection.find(and(eq("last_name", "Tordjman"), in("student_id", ids))).into(new ArrayList<>());
			studentList.forEach(x->System.out.println(x.toJson(prettyPrint)));
			
			
			
		}

	}

}
