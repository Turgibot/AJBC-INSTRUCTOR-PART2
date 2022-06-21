package ajbc.learn.mongodb.examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bson.Document;
import org.bson.conversions.Bson;
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
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Filters.*;

import ajbc.learn.mongodb.models.Student;
import ajbc.learn.nosql.MyConnectionString;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import static com.mongodb.client.model.Updates.*;

public class Update {
	private static final int CLASS_ID_MAX = 10;
	private static final int STUDENT_ID_MAX = 10000;
	private static final int MAX_GRADE = 100;
	private static final Random rand = new Random();

	public static void main(String[] args) {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(MyConnectionString.uri())
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).build();
		
		JsonWriterSettings prettyPrint = JsonWriterSettings.builder().indent(true).build();
		
		try (MongoClient mongoClient = MongoClients.create(settings)) {

			MongoDatabase myDB = mongoClient.getDatabase("my_own_db");
			MongoCollection<Document> myCollection = myDB.getCollection("myCollection");

			
			
			
//			// update one document
            Bson filter = eq("student_id", STUDENT_ID_MAX);
            Bson updateOperation = set("comment", "You should learn MongoDB!");
//            UpdateResult updateResult = myCollection.updateOne(filter, updateOperation);
//            System.out.println("=> Updating the doc with {\"student_id\":10000}. Adding comment.");
//            System.out.println(myCollection.find(filter).first().toJson(prettyPrint));
//            System.out.println(updateResult);
//			
            
//            
//            
//            
//            
//			
//            // upsert
            filter = and(eq("student_id", STUDENT_ID_MAX+99999), eq("class_id", CLASS_ID_MAX+9));
            updateOperation = push("comments", "You will learn a lot if you read the MongoDB blog!");
            UpdateOptions options = new UpdateOptions().upsert(true);
            UpdateResult updateResult = myCollection.updateOne(filter, updateOperation, options);
            System.out.println("\n=> Upsert document with {\"student_id\":10002.0, \"class_id\": 10.0} because it doesn't exist yet.");
            System.out.println(updateResult);
            System.out.println(myCollection.find(filter).first().toJson(prettyPrint));
//            
//         // update many documents
            filter = eq("student_id", 10000);
            updateResult = myCollection.updateMany(filter, updateOperation);
            System.out.println("\n=> Updating all the documents with {\"student_id\":10001}.");
            System.out.println(updateResult);
//
//            // findOneAndUpdate
            filter = eq("student_id", 10000);
            Bson update1 = inc("x", 10); // increment x by 10. As x doesn't exist yet, x=10.
            Bson update2 = rename("class_id", "new_class_id"); // rename variable "class_id" in "new_class_id".
            Bson update3 = mul("scores.0.score", 2); // multiply the first score in the array by 2.
            Bson update4 = addToSet("comments", "This comment is uniq"); // creating an array with a comment.
            Bson update5 = addToSet("comments", "This comment is uniq"); // using addToSet so no effect.
            Bson updates = combine(update1, update2, update3, update4, update5);
            // returns the old version of the document before the update.
            Document oldVersion = myCollection.findOneAndUpdate(filter, updates);
            System.out.println("\n=> FindOneAndUpdate operation. Printing the old version by default:");
            System.out.println(oldVersion.toJson(prettyPrint));

            // but I can also request the new version
            filter = eq("student_id", 10000);
            FindOneAndUpdateOptions optionAfter = new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER);
            Document newVersion = myCollection.findOneAndUpdate(filter, updates, optionAfter);
            System.out.println("\n=> FindOneAndUpdate operation. But we can also ask for the new version of the doc:");
            System.out.println(newVersion.toJson(prettyPrint));
		}

	}

}
