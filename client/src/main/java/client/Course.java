package client;
import java.util.ArrayList;
import java.util.List;

public class Course {
	private final Long NUMBER;
	private String name;
	private List<Student> students;
	private static long counter = 2000;
	
	public Course() {
		this.NUMBER = generateId();
		students = new ArrayList<Student>(); 
	}
	private synchronized long generateId() {
		return counter++;
	}
	public Course(String name) {
		this();
		setName(name);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Student> getStudents() {
		return students;
	}
	public void setStudents(List<Student> students) {
		this.students = students;
	}
	public static long getCounter() {
		return counter;
	}
	public static void setCounter(long counter) {
		Course.counter = counter;
	}
	public Long getNUMBER() {
		return NUMBER;
	}
	@Override
	public String toString() {
		return "Course [NUMBER=" + NUMBER + ", name=" + name + ", students=" + students + "]";
	}
	
	
}