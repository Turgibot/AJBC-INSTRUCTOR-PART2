package ajbc.learn.mongodb.examples;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import ajbc.learn.mongodb.models.Comment;
import ajbc.learn.mongodb.models.Movie;
import ajbc.learn.nosql.MyConnectionString;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;

import static com.mongodb.client.model.Accumulators.push;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Projections.*;

public class exe2 {
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
			MongoDatabase myDB = mongoClient.getDatabase("sample_mflix");
			MongoCollection<Document> movies = myDB.getCollection("movies");
			MongoCollection<Document> comments = myDB.getCollection("comments");
			
			//numMoviesInLast10Years(movies);
			showCommentWithTitle(comments);
		}
	}

	private static void showCommentWithTitle(MongoCollection<Document> comments) {
		LocalDateTime dateFilter = LocalDateTime.of(2017, 1, 1, 0, 0);
		Bson filter = match(Filters.gte("date", dateFilter));
		Bson leftJoin = lookup("movies", "movie_id", "_id", "movie");
		Bson projectName = project(fields(include("name", "email", "text", "date"), computed("movie_title", "$movie.title")));
		List<Document> commentJoined = comments.aggregate( Arrays.asList(filter, leftJoin,projectName)).into(new ArrayList<>());
		commentJoined.forEach(printDocuments());
		
	}

	private static void numMoviesInLast10Years(MongoCollection<Document> movies) {
		Bson match = match(eq("type", "movie"));
		Bson group = group("$year", sum("num_movies", 1));
		Bson project = project(fields(excludeId(), include("num_movies"),
				computed("year", "$_id")));
		Bson sort = sort(Sorts.descending("num_movies"));
		Bson limit = limit(10);

		List<Document> results = movies.aggregate(Arrays.asList(match, group, project, sort, limit)).into(new ArrayList<>());
		results.forEach(printDocuments());
	}

	private static Consumer<Document> printDocuments() {
		return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
	}
}
