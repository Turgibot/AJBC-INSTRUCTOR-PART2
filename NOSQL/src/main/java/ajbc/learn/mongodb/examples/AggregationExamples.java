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

public class AggregationExamples {
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
			MongoDatabase myDB = mongoClient.getDatabase("sample_training");
			MongoCollection<Document> zips = myDB.getCollection("zips");
			// matchExample(zips);
			// matchGroupExample(zips);
			// threeMostPopulatedCitiesInTexas(zips);

			MongoCollection<Document> posts = myDB.getCollection("posts");
			threeMostPopularTags(posts);
		}
	}

	/**
	 * unwind the tags and than group by each tag
	 * 
	 * @param posts
	 */
	private static void threeMostPopularTags(MongoCollection<Document> posts) {
		Bson unwind = Aggregates.unwind("$tags");
		Bson group = group("$tags", sum("count", 1L), push("titles", "$title"));
		Bson sort = sort(Sorts.descending("count"));
		Bson limit = Aggregates.limit(3);
		Bson project = project(Projections.fields(Projections.excludeId(), Projections.computed("tag", "$_id"), Projections.include("count", "titles")));
		List<Document> results = posts.aggregate(Arrays.asList(unwind, group, sort, limit)).into(new ArrayList<>());
		System.out.println("==> unwind and group by tag");
		results.forEach(printDocuments());
	}

	private static void matchGroupExample(MongoCollection<Document> zips) {
		Bson match = Aggregates.match(eq("state", "TX"));
		Bson group = Aggregates.group("$city", Accumulators.sum("totalPop", "$pop"));
		List<Document> results = zips.aggregate(Arrays.asList(match, group)).into(new ArrayList<>());
		results.forEach(printDocuments());

	}

	private static void matchExample(MongoCollection<Document> zips) {
		// match is like find
		Bson match = match(eq("state", "TX"));
		List<Document> results = zips.aggregate(Arrays.asList(match)).into(new ArrayList<>());
		results.forEach(printDocuments());
	}

	/**
	 * find the 3 most densely populated cities in Texas.
	 * 
	 * @param zips sample_training.zips collection from the MongoDB Sample Dataset
	 *             in MongoDB Atlas.
	 */
	private static void threeMostPopulatedCitiesInTexas(MongoCollection<Document> zips) {
		Bson match = match(eq("state", "TX"));
		Bson group = group("$city", sum("totalPop", "$pop"));
		Bson project = project(Projections.fields(Projections.excludeId(), Projections.include("totalPop"),
				Projections.computed("city", "$_id")));
		Bson sort = sort(Sorts.descending("totalPop"));
		Bson limit = limit(3);

		List<Document> results = zips.aggregate(Arrays.asList(match, group, project, sort, limit))
				.into(new ArrayList<>());
		System.out.println("==> 3 most densely populated cities in Texas");
		results.forEach(printDocuments());
	}

	private static Consumer<Document> printDocuments() {
		return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
	}
}
