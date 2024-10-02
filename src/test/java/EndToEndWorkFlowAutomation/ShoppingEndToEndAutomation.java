package EndToEndWorkFlowAutomation;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matcher.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ShoppingEndToEndAutomation {
	
	//Generate session tokens
	
	LoginPagePojo loginPage=new LoginPagePojo();
	
	public String userId;
	public String token;
	public String productId;
	public String orderId;
	
	Orders orders=new Orders();
	OrderDetails orderDetails=new OrderDetails();
	OrderDetailsOfUser orderDetailOfUser=new OrderDetailsOfUser();
	
	
	
	@Test(priority=0, description="Generate session tokens")
	public void getSessionToken()
	{
		loginPage.setUserEmail("testme@testme.com");
		loginPage.setUserPassword("Test@123");
		
		String response=given().log().all().contentType("application/json")
		.body(loginPage)
		.when()
		.post("https://rahulshettyacademy.com/api/ecom/auth/login")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		//Extracting token & userid from response'
		JsonPath js=new JsonPath(response);
		 userId=js.getString("userId");
		 token=js.getString("token");	
	}
	
	@Test(priority=1, description="Adding the new product")
	public void addNewProduct()
	{
		
		String response=given().log().all().header("Authorization",token)
		.formParam("productName", "TestProduct")
		.formParam("productAddedBy", userId)
		.formParam("productCategory", "fashion")
		.formParam("productSubCategory", "shirts")
		.formParam("productPrice", 11500)
		.formParam("productDescription", "Addias Originals")
		.formParam("productFor", "women")
		.multiPart("productImage",new File("C:\\Users\\h\\Pictures\\Camera Roll\\picture088.jpg"))
		.when()
		.post("https://rahulshettyacademy.com/api/ecom/product/add-product")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		//Extracting productId from response'
		JsonPath js=new JsonPath(response);
		productId=js.getString("productId");
		
	}
	
	@Test(priority=2,description="Create Order")
	public void createOrder()
	{
		
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(productId);
		List<OrderDetails>orderList=new ArrayList<OrderDetails>();
		orderList.add(orderDetails);
		orders.setOrder(orderList);
		
		String response=given().log().all()
		.header("Authorization",token)
		.contentType("application/json")
		//.body(orders)
		.body("{\r\n" + 
				"    \"orders\": [\r\n" + 
				"        {\r\n" + 
				"            \"country\": \"India\",\r\n" + 
				"            \"productOrderedId\": \""+productId+"\"\r\n" + 
				"        }\r\n" + 
				"    ]\r\n" + 
				"}")
		.when()
		.post("https://rahulshettyacademy.com/api/ecom/order/create-order")
		.then()
		.log().all()
		.assertThat().statusCode(201)
		.extract().response().asString();
		JsonPath js=new JsonPath(response);
		System.out.println(js.getString("orders"));

	}
	
	@Test(priority=3,description="Get Order Details for respective user..")
	public void getOrderDetails()
	{
		try
		{
			orderDetailOfUser=given()
			.header("Authorization",token)
			.pathParam("orderId", userId)
			.when()
			.get("https://rahulshettyacademy.com/api/ecom/order/get-orders-for-customer/{orderId}")
			.then().log().all()
			.assertThat().statusCode(200).extract().response().as(OrderDetailsOfUser.class);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test(priority=4,description="Delete order")
	public void deleteOrder()
	{
		try
		{
			orderId=orderDetailOfUser.getData().get(0).getId();
			given().log().all()
			.header("Authorization",token)
			.pathParam("orderId",orderId)
			.when()
			.delete("https://rahulshettyacademy.com/api/ecom/order/delete-order/{orderId}")
			.then().log().all().assertThat().statusCode(200);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test(priority=5,description="Delete Product")
	public void deleteProduct()
	{
		String response=given()
		.header("Authorization",token)
		.pathParam("productIdVal", productId)
		.when()
		.delete("https://rahulshettyacademy.com/api/ecom/product/delete-product/{productIdVal}")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js=new JsonPath(response);
		
		//Asserting response messahe
		Assert.assertEquals(js.getString("message"),"Product Deleted Successfully");
		
	}
	
	
	

}
