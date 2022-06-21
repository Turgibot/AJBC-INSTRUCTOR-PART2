package ajbc.learn.mongodb.examples;

import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
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
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.result.InsertOneResult;

import ajbc.learn.mongodb.models.Exam;
import ajbc.learn.mongodb.models.Student;
import ajbc.learn.nosql.MyConnectionString;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import static java.util.Arrays.asList;

import java.util.ArrayList;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class POJOMapping {
	private static final int MAX_GRADE = 100;
	private static final int CLASS_ID_MAX = 1;
	private static final int STUDENT_ID_MAX = 10000;

	private static final Random rand = new Random();

	public static void main(String[] args) {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		// prepare codec registry
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(MyConnectionString.uri())
				// add codec registry
				.codecRegistry(codecRegistry)
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
				.build();

		try (MongoClient mongoClient = MongoClients.create(settings)) {

			MongoDatabase myDB = mongoClient.getDatabase("my_own_db");
			// get a collection of students not of docs!!!
			MongoCollection<Student> studentsCollection = myDB.getCollection("myCollection", Student.class);

			// CREATE
			List<Exam> grades = new ArrayList<Exam>();
			grades.add(new Exam("English", 80));
			grades.add(new Exam("Java", 90));
			Student student = new Student(54321, 777, "Moshe", "Ufnik", grades);

			InsertOneResult result = studentsCollection.insertOne(student);
			System.out.println(result.wasAcknowledged());

			// READ

			
			Student student1 = studentsCollection.find(Filters.eq("student_id", STUDENT_ID_MAX)).first();
			System.out.println(student1);

			// UPDATE

			// remove all grades
			student1.setExams(new ArrayList<Exam>());
			Bson filter = Filters.eq("_id", student1.getId()); 
			FindOneAndReplaceOptions returnDocAfterReplace = new FindOneAndReplaceOptions()
					.returnDocument(ReturnDocument.AFTER);
			Student updatedStud1 = studentsCollection.findOneAndReplace(filter, student1, returnDocAfterReplace);
			System.out.println(updatedStud1);

			// DELETE
			
			System.out.println(studentsCollection.deleteOne(filter));
		}

	}

}
