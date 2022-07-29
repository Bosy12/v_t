package API_RestAssuredProject.RestAssured;

import Enitities.Post;
import Enitities.User;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.core.util.RequestPayload;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonMappingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;
import java.util.Random;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.json.async.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

public class AppTest {
	public static int userid;
	ObjectMapper obj;

	@BeforeTest
	public void getRandomUser() {
		AppTest.userid = new Random().nextInt(1, 10);
		obj = new ObjectMapper();
	}

	@Test
	public void getUserEmail() {

		RestAssured.baseURI = "https://jsonplaceholder.typicode.com/users/" + userid;

		Response vr = given().headers("Content-Type", "application/json; charset=utf-8").when().get().then().extract()
				.response();
		// vr.assertThat().statusCode(200);

		try {
			// System.out.print(vr.getBody().asString());
			User u2 = obj.readValue(vr.getBody().asString(), User.class);

			System.out.println("Email:" + u2.getEmail());
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * try { // serial
		 * 
		 * 
		 * // User u = new User(); // u.setId(0); // u.setName("a"); // String jsonobj =
		 * o.writerWithDefaultPrettyPrinter().writeValueAsString(u);
		 * 
		 * // deserialzie // User u2 = o.readValue(jsonobj, User.class); // //
		 * System.out.print(u2.getId()); // // System.out.print(jsonobj); } catch
		 * (JsonProcessingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	@Test
	public void getUserPosts() {

		RestAssured.baseURI = "https://jsonplaceholder.typicode.com/posts/";

		Response vr = given().headers("Content-Type", "application/json; charset=utf-8")
				.queryParam("userId", AppTest.userid).when().get().then().extract().response();

		try {
			//System.out.print(vr.getBody().asString());
			Post[] posts = obj.readValue(vr.getBody().asString(), Post[].class);

			for (int pindex = 0; pindex < posts.length; pindex++) {
				assertTrue(posts[pindex].getId() >= 1 && posts[pindex].getId() <= 100);
			}

		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	@Parameters({"Title","Body"})  
	public void doUserPost(String Title , String Body) throws JsonProcessingException {
		Post post = new Post();
		
		assertNotNull(Title);
		assertNotEquals("",Title);
		
		assertNotNull(Body);
		assertNotEquals("",Body);
		
		post.setBody(Title);
		post.setTitle(Body);
		post.setUserId(AppTest.userid);

		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
		String poststring = obj.writerWithDefaultPrettyPrinter().writeValueAsString(post);

		given().headers("Content-Type", "application/json; charset=utf-8").body(poststring)
		.when().post("/posts")
		.then().log().all().assertThat().statusCode(201);// extract().response();

		 
	}

}