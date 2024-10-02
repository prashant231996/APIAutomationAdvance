package LibraryApi;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ComplexJsonParsing.CustomResponse;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matcher.*;

public class AddBook {
	
	@Test(description="Test add book api",dataProvider = "BooksData",priority=1)
	public void testAddBookApi(String isbn, String aisle)
	{
		try
		{
			RestAssured.baseURI="http://216.10.245.166";
			Response res=given().contentType("application/json").body(CustomResponse.getAddBookBody(isbn,aisle))
			.when().post("Library/Addbook.php");
			Assert.assertEquals(res.getStatusCode(), 200);
			JsonPath js=new JsonPath(res.asString());
			String id=js.get("ID");
			System.out.println("Generated Id is "+id);
			//Validating success message
			Assert.assertEquals(js.get("Msg"), "successfully added");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test(description="test get book api", priority=2,dataProvider = "BooksData")
	public void testGetBookApi(String isbn, String aisle)
	{
		try
		{
			String bookId=isbn+aisle;
			RestAssured.baseURI="http://216.10.245.166";
			Response res=given().queryParam("ID", bookId).
			when().get("Library/GetBook.php");
			//Validating status code
			Assert.assertEquals(res.getStatusCode(), 200);
			//printing response
			System.out.println("Response is ==>"+res.asString());
			//Validating Id 
			JsonPath js=new JsonPath(res.asString());
//			String actualBookId=js.getString("isbn")+js.getString("aisle");
//			System.out.println("Acual Book Id is "+actualBookId);
	//		Assert.assertEquals(js.get("book_name"), "[Learn Appium Automation with Java]");
			//Assert.assertEquals(actualBookId,bookId);*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test(priority=3, description="test delete book api",dataProvider = "BooksData")
	public void testDeleteBook(String isbn,String aisle)
	{
		try
		{
			String bookId=isbn+aisle;
			RestAssured.baseURI="http://216.10.245.166";
			Response res=given().contentType("application/json").body(CustomResponse.getDeleteBookBody(bookId)).
			when().post("Library/DeleteBook.php");
			//Validating status code
			Assert.assertEquals(res.getStatusCode(), 200);
			JsonPath js=new JsonPath(res.asString());
			//Validating response message
			Assert.assertEquals(js.get("msg"), "book is successfully deleted");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	@DataProvider(name="BooksData")
	public Object[][] getBooksData()
	{
		return new Object[][] {{"dgd12","7373"},{"dfhd12","74373"},{"jfd12","7363"}};
	}

}
