package APISampleTests;

import org.testng.Assert;
import org.testng.annotations.Test;

import GoogleMapApiPOJO.Book;
import GoogleMapApiPOJO.Location;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matcher.*;

import java.util.ArrayList;
import java.util.List;

public class Add_Request {
	
	   public String Base_Url="https://rahulshettyacademy.com";
	   public String postResources="/maps/api/place/add/json";
	   public String postRequestEndPoints=Base_Url+postResources;
	   
	   Book book=new Book();
	   Location loc=new Location();
	   
		
	   @Test
	   public void add_request()
	   {
		   try
		   {
			   //Serializing the body using POJO classes
			   loc.setLat(-38.839);
			   loc.setLng(33.646);
			   book.setLocation(loc);
			   book.setAccuracy(50);
			   book.setName("Frontline house");
			   book.setPhone_number("3363636");
			   book.setAddress("Yadrav Fata, Ichalkaranji");
			   List<String>types=new ArrayList<String>();
			   types.add("shoe park");
			   types.add("shop");
			   book.setTypes(types);
			   book.setWebsite("https://google.com");
			   book.setLanguage("English");
			   
			   //Sending agg book payload using pojo class
			   Response res=given().
			   queryParam("key", "qaclick123").
			   contentType("application/json").
			   body(book).
			   when().
			   post(postRequestEndPoints);
			   
			   //Response 
			   System.out.println("Response is :"+res.asString());
			   //Validating Status 
			   Assert.assertEquals(res.jsonPath().get("status"),"OK");
			   //Validating response 
			   Assert.assertEquals(res.getStatusCode(), 200);
			   
		   }
		   catch(Exception e)
		   {
			   e.printStackTrace();
		   }
	   }
	   

}
