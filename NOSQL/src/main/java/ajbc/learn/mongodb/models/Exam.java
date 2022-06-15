package ajbc.learn.mongodb.models;

public class Exam {
	private String topic;
	private int score;
	public Exam(String topic, int score) {
		this.topic = topic;
		this.score = score;
	}
	public Exam() {}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	@Override
	public String toString() {
		return "Exam [topic=" + topic + ", score=" + score + "]";
	}
	
	

}
