package RegressionTest;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import io.restassured.response.Validatable;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
 

public class CompanyAPISeparated {
	public static void main(String[] args) { 
		//@Given
		RestAssured.baseURI="https://company.clearbit.com/v2";
		RequestSpecification objRequest=RestAssured.given();
		objRequest.queryParam("domain", "nokia.com");
		objRequest.queryParam("company_name", "Nokia");
		objRequest.queryParam("webhook_id","https://webhook.site/2e900c7f-c106-4576-bd4c-dac7e1b10e4d");
		objRequest.auth().oauth2("sk_1e7aa20d9a2e9962b9374949c59a9fab");
		//Response objResponse=objRequest.get("https://company.clearbit.com/v2/companies/find");
		Response objResponse=objRequest.get("/companies/find");
		System.out.println(objResponse.statusCode());
		System.out.println(objResponse.body().jsonPath().prettify());
		//System.out.println(objResponse.body().jsonPath().prettify());
		System.out.println("Done");
	}
}
