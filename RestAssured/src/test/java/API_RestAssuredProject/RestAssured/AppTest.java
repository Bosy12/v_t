package API_RestAssuredProject.RestAssured;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Random;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Enitities.Post;
import Enitities.User;
import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonMappingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AppTest {
	public static int userid;
	ObjectMapper obj;

	@BeforeTest
	@Parameters({ "endPoint" })
	public void getRandomUser(String endPoint) {
		AppTest.userid = new Random().nextInt(1, 10);
		obj = new ObjectMapper();
		RestAssured.baseURI = endPoint;
	}

	@Test
	public void getUserEmail() throws JsonMappingException, JsonProcessingException {

		Response vr = given().headers("Content-Type", "application/json; charset=utf-8").when().get("/users/" + userid)
				.then().assertThat().statusCode(200).extract().response();

		User u2 = obj.readValue(vr.getBody().asString(), User.class);

		System.out.println("Email:" + u2.getEmail());

	}

	@Test
	public void getUserPosts() throws JsonMappingException, JsonProcessingException {

		Response vr = given().headers("Content-Type", "application/json; charset=utf-8")
				.queryParam("userId", AppTest.userid).when().get("posts").then().extract().response();

		// System.out.print(vr.getBody().asString());
		Post[] posts = obj.readValue(vr.getBody().asString(), Post[].class);

		for (int pindex = 0; pindex < posts.length; pindex++) {
			assertTrue(posts[pindex].getId() >= 1 && posts[pindex].getId() <= 100);
		}

	}

	@Test
	@Parameters({ "Title", "Body" })
	public void doUserPost(String Title, String Body) throws JsonProcessingException {
		Post post = new Post();

		assertNotNull(Title);
		assertNotEquals("", Title);

		assertNotNull(Body);
		assertNotEquals("", Body);

		post.setBody(Title);
		post.setTitle(Body);
		post.setUserId(AppTest.userid);

		String poststring = obj.writerWithDefaultPrettyPrinter().writeValueAsString(post);

		given().headers("Content-Type", "application/json; charset=utf-8").body(poststring).when().post("/posts").then()
				.assertThat().statusCode(201);// extract().response();

	}

}