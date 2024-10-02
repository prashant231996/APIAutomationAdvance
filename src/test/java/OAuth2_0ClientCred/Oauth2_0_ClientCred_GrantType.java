package OAuth2_0ClientCred;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ComplexJsonParsing.CustomResponse;
import POJO.CourseDetails;
import POJO.WebAutomation;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matcher.*;

import java.awt.List;


public class Oauth2_0_ClientCred_GrantType {
	
	String accessToken;
	
	RequestSpecification req=new RequestSpecBuilder()
			.setBaseUri("https://rahulshettyacademy.com/oauthapi").build();
	
	@Test(priority=1,description = "Getting the access token")
	public void getAccessToken()
	{
		try
		{
			//RestAssured.baseURI="https://rahulshettyacademy.com/oauthapi";
			
			String response=given().spec(req).log().all().
			formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			.formParam("grant_type", "client_credentials")
			.formParam("scope", "trust")
			.when().post("/oauth2/resourceOwner/token")
			.then().log().all().assertThat().statusCode(200).extract().response().asString();
			JsonPath js=new JsonPath(response); 
			//Getting the access token
		   accessToken=js.getString("access_token");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test(priority=2,description="Getting the course details using access token")
	public void fetchCorseDetails()
	{
		try
		{
			//RestAssured.baseURI="https://rahulshettyacademy.com/oauthapi";
			CourseDetails courseDetails=given().spec(req).log().all()
			.queryParam("access_token", accessToken)
			.when()
			.get("/getCourseDetails")
			.then().log().all()
			.assertThat().statusCode(401).extract().response().as(CourseDetails.class);
			
			//Deserializing the response using POJO classes
			//Printing WebAutomation related cases
	    for(int i=0;i<courseDetails.getCourses().getWebAutomation().size();i++)
	    {
	    	System.out.println("Title of Course==>"+courseDetails.getCourses().getWebAutomation().get(i).getCourseTitle());
	    	System.out.println("Price Of Course==>"+courseDetails.getCourses().getWebAutomation().get(i).getPrice());
	    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
