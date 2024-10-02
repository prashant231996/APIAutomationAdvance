package APISampleTests;


import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matcher.*;

public class Delete_Request {
	
	 public String Base_Url="https://rahulshettyacademy.com";
	 public String deleteResources="/maps/api/place/delete/json";
	 public String deleteRequestEndPoints=Base_Url+deleteResources;
	 
	 @Test
	 public void deleteRequest()
	 {
		 try
		 {
			 Response res=given().
			 queryParam("key", "qaclick123")
			 .body("{\r\n" + 
			 		"    \"place_id\":\"a671a1f274df266f94a19bf4853ceba6\"\r\n" + 
			 		"}")
			 .contentType("application/json")
			 .delete(deleteRequestEndPoints);
			 
			 //Validating response
			 Assert.assertEquals(res.getStatusCode(), 200);
			 Assert.assertEquals(res.jsonPath().get("status"), "OK");
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	 }
	 
	 

}
