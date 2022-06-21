package ajbc.learn.mongodb.models;
//this is a partially implemented class

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Movie {

	private ObjectId id;
	private String title;
	private int year;
	private List<String> cast;
	@BsonProperty(value = "num_mflix_comments")
	private int numComments;

	public Movie(ObjectId id, String title, int year, List<String> cast, int numComments) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.cast = cast;
		this.numComments = numComments;
	}
	

	public Movie( String title, int year, List<String> cast, int numComments) {

		this.title = title;
		this.year = year;
		this.cast = cast;
		this.numComments = numComments;
	}
	

	public Movie() {}


	public ObjectId getId() {
		return id;
	}


	public void setId(ObjectId id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public List<String> getCast() {
		return cast;
	}


	public void setCast(List<String> cast) {
		this.cast = cast;
	}


	public int getNumComments() {
		return numComments;
	}


	public void setNumComments(int numComments) {
		this.numComments = numComments;
	}


	@Override
	public String toString() {
		return "Movie [id=" + id + ", title=" + title + ", year=" + year + ", cast=" + cast + ", numComments="
				+ numComments + "]";
	}
	
	
	
	
}
