package ajbc.learn.mongodb.examples;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;

import ajbc.learn.mongodb.models.Order;

public class Utils {
	private static Random rand = new Random(System.currentTimeMillis());
	public static String[] firstNames = new String[] { "Adam", "Alex", "Aaron", "Ben", "Carl", "Dan", "David", "Edward",
			"Fred", "Frank", "George", "Hal", "Hank", "Ike", "John", "Jack", "Joe", "Larry", "Monte", "Matthew", "Mark",
			"Nathan", "Otto", "Paul", "Peter", "Roger", "Roger", "Steve", "Thomas", "Tim", "Ty", "Victor", "Walter" };

	public static String[] lastNames = new String[] { "Anderson", "Ashwoon", "Aikin", "Bateman", "Bongard", "Bowers",
			"Boyd", "Cannon", "Cast", "Deitz", "Dewalt", "Ebner", "Frick", "Hancock", "Haworth", "Hesch", "Hoffman",
			"Kassing", "Knutson", "Lawless", "Lawicki", "Mccord", "McCormack", "Miller", "Myers", "Nugent", "Ortiz",
			"Orwig", "Ory", "Paiser", "Pak", "Pettigrew", "Quinn", "Quizoz", "Ramachandran", "Resnick", "Sagar",
			"Schickowski", "Schiebel", "Sellon", "Severson", "Shaffer", "Solberg", "Soloman", "Sonderling", "Soukup",
			"Soulis", "Stahl", "Sweeney", "Tandy", "Trebil", "Trusela", "Trussel", "Turco", "Uddin", "Uflan", "Ulrich",
			"Upson", "Vader", "Vail", "Valente", "Van Zandt", "Vanderpoel", "Ventotla", "Vogal", "Wagle", "Wagner",
			"Wakefield", "Weinstein", "Weiss", "Woo", "Yang", "Yates", "Yocum", "Zeaser", "Zeller", "Ziegler", "Bauer",
			"Baxster", "Casal", "Cataldi", "Caswell", "Celedon", "Chambers", "Chapman", "Christensen", "Darnell",
			"Davidson", "Davis", "DeLorenzo", "Dinkins", "Doran", "Dugelman", "Dugan", "Duffman", "Eastman", "Ferro",
			"Ferry", "Fletcher", "Fietzer", "Hylan", "Hydinger", "Illingsworth", "Ingram", "Irwin", "Jagtap", "Jenson",
			"Johnson", "Johnsen", "Jones", "Jurgenson", "Kalleg", "Kaskel", "Keller", "Leisinger", "LePage", "Lewis",
			"Linde", "Lulloff", "Maki", "Martin", "McGinnis", "Mills", "Moody", "Moore", "Napier", "Nelson", "Norquist",
			"Nuttle", "Olson", "Ostrander", "Reamer", "Reardon", "Reyes", "Rice", "Ripka", "Roberts", "Rogers", "Root",
			"Sandstrom", "Sawyer", "Schlicht", "Schmitt", "Schwager", "Schutz", "Schuster", "Tapia", "Thompson",
			"Tiernan", "Tisler" };

	public static String[] getRandomName() {
		return new String[] { firstNames[rand.nextInt(firstNames.length)], lastNames[rand.nextInt(lastNames.length)] };
		
	}
	
	public static MongoCollection<Order> createPizzaOrdersCollection(MongoClient mongoClient) {
		MongoDatabase myDB = mongoClient.getDatabase("pizza");
		MongoCollection<Order> orders = myDB.getCollection("orders", Order.class);
		List<Order> ordersList = Arrays.asList(
				new Order("Vegan","small", 19.90f, LocalDateTime.now().minus(1, ChronoUnit.HOURS)),
				new Order("Vegan","medium", 24.90f, LocalDateTime.now().minus(2, ChronoUnit.HOURS)),
				new Order("Vegan","large", 29.90f, LocalDateTime.now().minus(2, ChronoUnit.HOURS)),
				
				new Order("Greek","small", 19.90f, LocalDateTime.now().minus(1, ChronoUnit.HOURS)),
				new Order("Greek","medium", 24.90f, LocalDateTime.now().minus(2, ChronoUnit.HOURS)),
				new Order("Greek","large", 29.90f, LocalDateTime.now().minus(2, ChronoUnit.HOURS)),
				
				new Order("Pepperoni","small", 29.90f, LocalDateTime.now().minus(1, ChronoUnit.HOURS)),
				new Order("Pepperoni","medium", 34.90f, LocalDateTime.now().minus(2, ChronoUnit.HOURS)),
				new Order("Pepperoni","large", 39.90f, LocalDateTime.now())
				);
		InsertManyResult result = orders.insertMany(ordersList);
		
		System.out.println(result);
		
		return orders;
	}
	
	public static MongoCollection<Document> createRestaurantsCollection(MongoClient mongoClient) {
		MongoDatabase myDB = mongoClient.getDatabase("foods");
		MongoCollection<Document> collection = myDB.getCollection("retaurants");
		InsertManyResult result = collection.insertMany(Arrays.asList(
			    new Document("name", "Sun Bakery Trattoria").append("contact", new Document().append("phone", "386-555-0189").append("email", "SunBakeryTrattoria@example.org").append("location", Arrays.asList(-74.0056649, 40.7452371))).append("stars", 4).append("categories", Arrays.asList("Pizza", "Pasta", "Italian", "Coffee", "Sandwiches")),
			    new Document("name", "Blue Bagels Grill").append("contact", new Document().append("phone", "786-555-0102").append("email", "BlueBagelsGrill@example.com").append("location", Arrays.asList(-73.92506, 40.8275556))).append("stars", 3).append("categories", Arrays.asList("Bagels", "Cookies", "Sandwiches")),
			    new Document("name", "XYZ Bagels Restaurant").append("contact", new Document().append("phone", "435-555-0190").append("email", "XYZBagelsRestaurant@example.net").append("location", Arrays.asList(-74.0707363, 40.59321569999999))).append("stars", 4).append("categories", Arrays.asList("Bagels", "Sandwiches", "Coffee")),
			    new Document("name", "Hot Bakery Cafe").append("contact", new Document().append("phone", "264-555-0171").append("email", "HotBakeryCafe@example.net").append("location", Arrays.asList(-73.96485799999999, 40.761899))).append("stars", 4).append("categories", Arrays.asList("Bakery", "Cafe", "Coffee", "Dessert")),
			    new Document("name", "Green Feast Pizzeria").append("contact", new Document().append("phone", "840-555-0102").append("email", "GreenFeastPizzeria@example.com").append("location", Arrays.asList(-74.1220973, 40.6129407))).append("stars", 2).append("categories", Arrays.asList("Pizza", "Italian")),
			    new Document("name", "ZZZ Pasta Buffet").append("contact", new Document().append("phone", "769-555-0152").append("email", "ZZZPastaBuffet@example.com").append("location", Arrays.asList(-73.9446421, 40.7253944))).append("stars", 0).append("categories", Arrays.asList("Pasta", "Italian", "Buffet", "Cafeteria")),
			    new Document("name", "XYZ Coffee Bar").append("contact", new Document().append("phone", "644-555-0193").append("email", "XYZCoffeeBar@example.net").append("location", Arrays.asList(-74.0166091, 40.6284767))).append("stars", 5).append("categories", Arrays.asList("Coffee", "Cafe", "Bakery", "Chocolates")),
			    new Document("name", "456 Steak Restaurant").append("contact", new Document().append("phone", "990-555-0165").append("email", "456SteakRestaurant@example.com").append("location", Arrays.asList(-73.9365108, 40.8497077))).append("stars", 0).append("categories", Arrays.asList("Steak", "Seafood")),
			    new Document("name", "456 Cookies Shop").append("contact", new Document().append("phone", "604-555-0149").append("email", "456CookiesShop@example.org").append("location", Arrays.asList(-73.8850023, 40.7494272))).append("stars", 4).append("categories", Arrays.asList("Bakery", "Cookies", "Cake", "Coffee")),
			    new Document("name", "XYZ Steak Buffet").append("contact", new Document().append("phone", "229-555-0197").append("email", "XYZSteakBuffet@example.org").append("location", Arrays.asList(-73.9799932, 40.7660886))).append("stars", 3).append("categories", Arrays.asList("Steak", "Salad", "Chinese"))
			));
		
		
		System.out.println(result);
		
		return collection;
	}

}
