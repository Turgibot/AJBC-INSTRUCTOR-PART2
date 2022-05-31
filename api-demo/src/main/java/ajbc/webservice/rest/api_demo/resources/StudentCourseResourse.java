package ajbc.webservice.rest.api_demo.resources;

import java.util.List;

import ajbc.webservice.rest.api_demo.DBservice.StudentCourseDBService;
import ajbc.webservice.rest.api_demo.models.Course;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentCourseResourse {

	private StudentCourseDBService dbService = new StudentCourseDBService();
	
	@GET
	public Response getCoursesByStudentId(@PathParam("id") long id) {
		List<Course> courses = dbService.getCoursesByStudentId(id);
		return Response.ok().entity(courses).build();
	}
	

}
