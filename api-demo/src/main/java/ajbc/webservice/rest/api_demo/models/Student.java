package ajbc.webservice.rest.api_demo.models;

import java.util.ArrayList;
import java.util.List;

//class must have an empty constructor and getters and setters (javaBean)

public class Student {

	private final Long ID;
	private String firstName;
	private String lastName;
	private double average;
	private static long counter = 1000;
	private List<Course> courses;
	
	public Student() {
		this.ID = generateId();
		courses = new ArrayList<Course>();
	}
	private synchronized long generateId() {
		return counter++;
		
	}
	public Student(String firstName, String lastName, double average) {
		this();
		setFirstName(firstName);
		setLastName(lastName);
		setAverage(average);
	}
	
	public Student(String firstName, String lastName, double average, List<Course> courses) {
		this();
		setFirstName(firstName);
		setLastName(lastName);
		setAverage(average);
		setCourses(courses);
	}
	
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public long getID() {
		return ID;
	}
	

	@Override
	public String toString() {
		return "Student [ID=" + ID + ", firstName=" + firstName + ", lastName=" + lastName + ", average=" + average
				+ "]";
	}
	
	
}

