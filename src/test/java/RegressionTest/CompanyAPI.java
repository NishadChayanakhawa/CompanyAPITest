package RegressionTest;
import io.restassured.RestAssured;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import Common.HelperClass;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.HashMap;

public class CompanyAPI {
	String strAPI;
	@BeforeTest
	@Parameters({"TestData","API"})
	public void openTestData(String strTestData,String strAPI) {
		this.strAPI=strAPI;
		HelperClass.setDataWorkbook(strTestData);
	}
	@Test(priority=0,
			description="Invalid Authorization tests",
			dataProvider="TestDataProvider",
			successPercentage=80)
	public void validateInvalidAuthorization(String strAuthKey,ITestContext objTestContext) {
		//String strAuthKey="sk_1e7aa20d9a2e9962b9374949c59a9fab";
		//strAuthKey="invalid_key";
		//String strAPI="https://company.clearbit.com/v2/companies/find";
		Map<String,String> ParameterMap=new HashMap<String,String>();
		ParameterMap.put("Auth Key", strAuthKey);
		objTestContext.setAttribute("ParameterMap", ParameterMap);
		RestAssured
			.given()
				.auth()
					.oauth2(strAuthKey)
					.queryParam("domain", "facebook.com")
			.when()
				.get(strAPI)
			.then()
				.assertThat()
					.statusCode(401)
					.body(matchesJsonSchemaInClasspath("JSONPathSchema/ErrorJsonSchema.json"));
	}
	
	@Test(priority=1,enabled=false,
			description="Valid Authorization tests",
			dataProvider="TestDataProvider")
	public void validateValidAuthorization(String strAuthKey,ITestContext objTestContext) {
		Map<String,String> ParameterMap=new HashMap<String,String>();
		ParameterMap.put("Auth Key", strAuthKey);
		objTestContext.setAttribute("ParameterMap", ParameterMap);
		RestAssured
			.given()
				.auth()
					.oauth2(strAuthKey)
				.queryParam("domain", "facebook.com")
			.when()
				.get(strAPI)
			.then()
				.assertThat()
					.statusCode(200)
					.body(Matchers.not(Matchers.hasItem("error")))
					.body(Matchers.not(matchesJsonSchemaInClasspath("JSONPathSchema/ErrorJsonSchema.json")));
	}
	
	@AfterTest
	public void endTesting() {
		HelperClass.closeDataWorkbook();
	}
	
	@DataProvider(name="TestDataProvider")
	public Object[][] getTestData(Method objMethod) {
		return HelperClass.getDataFromWorkbook(objMethod.getName());
	}
}
