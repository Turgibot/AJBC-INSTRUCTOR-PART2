package ajbc.learn.mongodb.models;
//this is a partially implemented class

import java.time.LocalDateTime;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Comment {

	private ObjectId id;
	private String name;
	private String email;
	@BsonProperty(value = "movie_id")
	private ObjectId movieId;
	private String text;
	private LocalDateTime date;
	
	public Comment(ObjectId id, String name, String email, ObjectId movieId, String text, LocalDateTime date) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.movieId = movieId;
		this.text = text;
		this.date = date;
	}

	public Comment(String name, String email, ObjectId movieId, String text, LocalDateTime date) {
		this.name = name;
		this.email = email;
		this.movieId = movieId;
		this.text = text;
		this.date = date;
	}
	
	public Comment() {}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public ObjectId getMovieId() {
		return movieId;
	}

	public void setMovieId(ObjectId movieId) {
		this.movieId = movieId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", name=" + name + ", email=" + email + ", movieId=" + movieId + ", text=" + text
				+ ", date=" + date + "]";
	}
	
	
}
