package UATTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.Test;

@Test
@CucumberOptions(
		features="./src/test/resources/Features",
		glue= {"UATTests"},
		tags= "@RegressionTest",
		plugin= {"pretty","html:src/test/resources/Reports/UATTestReport.html"}
)
public class testNGRunner extends AbstractTestNGCucumberTests{

}
