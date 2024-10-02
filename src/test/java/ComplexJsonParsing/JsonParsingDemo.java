package ComplexJsonParsing;

import io.restassured.path.json.JsonPath;


public class JsonParsingDemo {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		//Using JsonPath class to convert response present in string format to JSon
		
		JsonPath js=new JsonPath(CustomResponse.getCustomResponse());
		
		//System.out.println(CustomResponse.getCustomResponse());
		//1. Print No of courses returned by API
		System.out.println("No. Of Cources :"+js.getInt("courses.size()"));
		//2.Print Purchase Amount
		System.out.println("Purchase Amount is :"+js.getInt("dashboard.purchaseAmount"));
		//3. Print Title of the first course
		System.out.println("Title of first Cources :"+js.get("courses[0].title"));
		//4. Print All course titles and their respective Prices
        int courseConuts=js.getInt("courses.size()");
        for(int i=0;i<courseConuts;i++)
        {
        	System.out.println("Title of course is"+js.get("courses["+i+"].title"));
        	System.out.println("Price of course is"+js.get("courses["+i+"].price"));
        }
		//5. Print no of copies sold by RPA Course
        for(int i=0;i<courseConuts;i++)
        {
        	String title=js.get("courses["+i+"].title");
        	if(title.equalsIgnoreCase("RPA"))
        	{
        	System.out.println("Price of RPA course is "+js.get("courses["+i+"].price"));
        	break;
        	}
        }
		//6. Verify if Sum of all Course prices matches with Purchase Amount
        int purchaseAmount=js.getInt("dashboard.purchaseAmount");
        int actualPurchaseAmount=0;
        for(int i=0;i<courseConuts;i++)
        {
        	int price=js.getInt("courses["+i+"].price");
        	int copies=js.getInt("courses["+i+"].copies");
        	actualPurchaseAmount=actualPurchaseAmount+price*copies;
        }
        if(actualPurchaseAmount==purchaseAmount)
        {
        	System.out.println("Validation Passed");
        }
        else
        {
        	System.out.println("Validation Failed");
        }

	}

}
