package ajbc.learn.mongodb.models;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

public class Order {

	private ObjectId id;
	private String name;
	private String size;
	private float price;
	private LocalDateTime date;

	public Order(ObjectId id, String name, String size, float price, LocalDateTime date) {
		this.id = id;
		this.name = name;
		this.size = size;
		this.price = price;
		this.date = date;
	}
	

	public Order(String name, String size, float price, LocalDateTime date) {
		this.name = name;
		this.size = size;
		this.price = price;
		this.date = date;
	}
	

	public Order() {}


	public ObjectId getId() {
		return id;
	}


	public void setId(ObjectId id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public float getPrice() {
		return price;
	}


	public void setPrice(float price) {
		this.price = price;
	}


	public LocalDateTime getDate() {
		return date;
	}


	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
}
