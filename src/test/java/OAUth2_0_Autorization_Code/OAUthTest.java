package OAUth2_0_Autorization_Code;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;



public class OAUthTest {
	
	static String accessToken;
	static String code;
	
	@Test(priority=1, description="Get Authorization code")
	public void getAUthorizationCode()
	{
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&redirect_uri=https://rahulshettyacademy.com/getCourse.php&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&state=abcd&response_type=code&Auth_url=https://accounts.google.com/o/oauth2/v2/auth");
		driver.findElement(By.xpath("//div[text()='Use another account']")).click();
		driver.findElement(By.id("identifierId")).sendKeys("morep2476@gmail.com");
		driver.findElement(By.xpath("//*[text()='Next']")).click();
		driver.findElement(By.name("Passwd")).sendKeys("prashant@1996");
		driver.findElement(By.xpath("//*[text()='Next']")).click();
		String url=driver.getCurrentUrl();
		String[]urlParts=url.split("code=");
		code=urlParts[1].split("&scope")[0];
		
	}
	
	@Test(priority=2,description="Get Access token")
	public void getAccessToken()
	{
		 Response res=given().
		 urlEncodingEnabled(false).
		 queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token");
		
		System.out.println("Access token response is "+res.asString());
		System.out.println("Responce is :"+res.getStatusCode());
		JsonPath js=new JsonPath(res.asString());
		accessToken=js.get("access_token");
	}
	
	@Test(priority=3, description="get cource details using access token")
	public void getCourceDetails()
	{
		Response res=given().queryParam("access_token", accessToken)
		.when().log().all().get("https://rahulshettyacademy.com/getCourse.php");
		
		System.out.println("Responce is :"+res.asString());
		System.out.println("Responce is :"+res.getStatusCode());
	}

}
