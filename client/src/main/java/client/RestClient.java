package client;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;

public class RestClient {
	public static final String WEB_SERVICE_URL = "http://localhost:8080/api-demo/webapi/students/1001";

	public static void main(String[] args) throws Exception {
//		getStudent(1000l);
		postExample();
	}

	public static void getExample() {

	}

	public static void postExample() throws ClientProtocolException, IOException {
		Student student = new Student("Guy", "Tordjman", 99.9);
		Gson gson = new Gson();
		String payload = gson.toJson(student);
		StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_FORM_URLENCODED);

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(WEB_SERVICE_URL);
		request.setHeader("Content-Type", "application/json");
		request.setHeader("Accept", "application/json");

		request.setEntity(entity);

		HttpResponse response = httpClient.execute(request);
		System.out.println(response.getStatusLine().getStatusCode());
	}
}
