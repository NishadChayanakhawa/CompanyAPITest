package UATTests;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.ParameterType;
import Common.HelperClass;
import io.restassured.RestAssured;

import org.hamcrest.Matchers;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
public class AuthorizationKeyValidations {
	RequestSpecification objRequest;
	Response objResponse;
	
	@Given("API Base is {string}")
	public void api_base_is(String strAPIBase) {
		//HelperClass.enableLogging();
		HelperClass.logln("API Base is " + strAPIBase);
		RestAssured.baseURI=strAPIBase;
		objRequest=RestAssured.given();
	}
	@Given("{string} parameter is set to {string}")
	public void parameter_is_set_to(String strParameterName,String strParameterValue) {
		objRequest.queryParam(strParameterName, strParameterValue);
	}
	
	@And("apiKey provided is {string}")
	public void apikey_is(String strAPIKey) {
		HelperClass.logln("apiKey provided is " + strAPIKey);
		objRequest.auth().oauth2(strAPIKey);
	}
	
	@When("API is called")
	public void when_api_is_called() {
		HelperClass.logln("API is called");
		objResponse=objRequest.get("/companies/find");
	}
	
	@Then("Header Status should be {int}")
	public void header_status_should_be(int iStatusCode) {
		HelperClass.logln("Header Status should be " + iStatusCode);
		objResponse.then()
			.assertThat()
				.statusCode(iStatusCode);
	}
	
	@And("Response JSON matches schema {string}")
	public void response_json_matches_schema(String strJSONSchemaName) {
		objResponse.then()
			.assertThat()
			.body(matchesJsonSchemaInClasspath("JSONPathSchema/" + strJSONSchemaName + ".json"));
	}
	
	@And("Body {string} should be {string}")
	public void body_should_be(String strBodyPath,String strValue) {
		objResponse.then()
			.assertThat()
				.body(strBodyPath, Matchers.equalTo(strValue));
	}
	
	
}
