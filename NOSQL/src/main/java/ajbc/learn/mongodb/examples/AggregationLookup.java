package ajbc.learn.mongodb.examples;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

import ajbc.learn.nosql.MyConnectionString;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;

import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Accumulators.sum;

public class AggregationLookup {
	public static void main(String[] args) {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		// prepare codec registry
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(MyConnectionString.uri())
				// add codec registry
				.codecRegistry(codecRegistry).serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
				.build();

		try (MongoClient mongoClient = MongoClients.create(settings)) {
			MongoDatabase db = mongoClient.getDatabase("test");
			MongoCollection<Document> books = db.getCollection("books");
			MongoCollection<Document> authors = db.getCollection("authors");
			books.drop();
			authors.drop();
			books.insertOne(new Document("_id", 1).append("title", "Super Book").append("authors_id", Arrays.asList(1, 2)));
			authors.insertOne(new Document("_id", 1).append("name", "Bob"));
			authors.insertOne(new Document("_id", 2).append("name", "Alice"));

			Bson pipeline = lookup("authors", "authors_id", "_id", "book_authors");
			List<Document> booksJoined = books.aggregate( Arrays.asList(pipeline)).into(new ArrayList<>());
			booksJoined.forEach(printDocuments());
		}
	}

	private static Consumer<Document> printDocuments() {
		return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
	}
}