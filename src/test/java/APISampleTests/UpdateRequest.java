package APISampleTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matcher.*;

public class UpdateRequest {
	
	 public String Base_Url="https://rahulshettyacademy.com";
	 public String updateResources="/maps/api/place/update/json";
	 public String putRequestEndPoints=Base_Url+updateResources;
	
	 @Test
	 public void update_Request()
	 {
		 try
		 {
			 Response res=given().queryParam("key", "qaclick123")
			 .contentType("application/json")
			 .body("{\r\n" + 
			 		"\"place_id\":\"cbf68e95b7bb61a7ed3ebae915af8a30\",\r\n" + 
			 		"\"address\":\"Yadrav Fata, Ichalkaranji-416121\",\r\n" + 
			 		"\"key\":\"qaclick123\"\r\n" + 
			 		"}")
			 .when().put(putRequestEndPoints);
			 
			 //Validating response
			 Assert.assertEquals(res.getStatusCode(), 200);
			 Assert.assertEquals(res.jsonPath().get("msg"), "Address successfully updated");
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	 }

}
