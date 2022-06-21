package ajbc.learn.mongodb.crud;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.result.InsertOneResult;

import ajbc.learn.mongodb.models.Exam;
import ajbc.learn.mongodb.models.Student;
import ajbc.learn.nosql.MyConnectionString;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;

public class Create {
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

		try (MongoClient mongoClient = MongoClients.create(settings)) {

			MongoDatabase myDB = mongoClient.getDatabase("my_own_db");
			MongoCollection<Document> myCollection = myDB.getCollection("myCollection");

			myCollection.insertOne(generateNewStudent(STUDENT_ID_MAX, CLASS_ID_MAX, "Guy", "Tordjman"));

			// Bulk Inserts

			List<Document> studentDocs = new ArrayList<>();

			for (int i = 0; i < 20; i++) {
				String[] fullName = Utils.getRandomName();
				studentDocs.add(generateNewStudent(rand.nextInt(STUDENT_ID_MAX), rand.nextInt(CLASS_ID_MAX),
						fullName[0], fullName[1]));
			}

			myCollection.insertMany(studentDocs);

			List<Exam> grades = new ArrayList<Exam>();
			grades.add(new Exam("English", 80));
			grades.add(new Exam("Java", 90));
			Student student = new Student(12345, 777, "Moshe", "Ufnik", grades);

			Gson gson = new Gson();
			String studentJson = gson.toJson(student);

			System.out.println(studentJson);

			Document studDoc = Document.parse(studentJson);
			InsertOneResult result = myCollection.insertOne(studDoc);
			System.out.println(result.wasAcknowledged());

		}

	}

	private static Document generateNewStudent(int studentId, int classId, String firstName, String lastName) {
		List<Document> exams = asList(new Document("topic", "english").append("score", rand.nextInt(MAX_GRADE + 1)),
				new Document("topic","mathematics").append("score", rand.nextInt(MAX_GRADE + 1)),
				new Document("topic","french").append("score", rand.nextInt(MAX_GRADE + 1)),
				new Document("topic","advanced_java").append("score", rand.nextInt(MAX_GRADE + 1)));

		return new Document("student_id", studentId).append("first_name", firstName).append("last_name", lastName)
				.append("class_id", classId).append("exams", exams);
	}

	
};