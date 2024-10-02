package APISampleTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matcher.*;

public class Get_Request {
	
   public static String Base_Url="https://rahulshettyacademy.com";
   public String getResources="/maps/api/place/get/json";
   public String getRequestEndPoints=Base_Url+getResources;
  // public static String getRequestEndPoints=Base_Url+"{getResources}";
	
	@Test
	public void getRequest()
	{
		try
		{
			RestAssured.baseURI="https://rahulshettyacademy.com";
			Response res=
			given().
			//pathParam("getResource", "/maps/api/place/get/json").
			queryParam("key", "qaclick123").
			queryParam("place_id", "3c2935dc814c3d9b1a64752b4aa1e02e").
			when().
			get(getResources);
			//get("https://rahulshettyacademy.com/{getResources}");
			//get(Base_Url+getResources);
			System.out.println("Response is "+res.asString());
			//System.out.println("Url hit to the server "+res.);
			//Asserting Name as "name":"Frontline house"
			res.then().statusCode(200);
			String actualName=res.jsonPath().get("name");
			Assert.assertEquals(actualName, "Frontline house");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
