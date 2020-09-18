package Listeners;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestResult;
import static Common.HelperClass.logln;
import static Common.HelperClass.enableLogging;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.ExtentTest;
import java.util.Map;
import java.lang.reflect.Method;
public class SampleReport implements ITestListener,ISuiteListener{
	ExtentReports objReport;
	ExtentSparkReporter objReporter;
	ExtentTest objTest;
	//ExtentTest objTestCase;
	int testCaseNumber;
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		String strTestName="TC#" + ++testCaseNumber + " - " + result.getMethod().getDescription();
		logln("    Method name: ["+strTestName+"]");
		
		objTest=objReport.createTest(strTestName);
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		objTest.log(Status.INFO,"Parameters: "+result.getTestContext().getAttribute("ParameterMap"));
		objTest.log(Status.PASS,"Passed");
		logln("    Passed");
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		objTest.log(Status.INFO,"Parameters: "+result.getTestContext().getAttribute("ParameterMap"));
		objTest.log(Status.FAIL,"Failed");
		//objTest.error("Failed");
		logln("    Failed");
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		logln("    Skipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		logln("    Conditionally Passed");
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
		String strTestName=context.getName();
		logln("  Start of Test: ["+strTestName+"]");
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		logln("  End of Test");
	}

	public void onStart(ISuite suite) {
		// TODO Auto-generated method stub
		testCaseNumber=0;
		//enableLogging();
		String strSuiteName=suite.getName();
		logln("Start of Suite: ["+strSuiteName+"]");
		
		objReporter=new ExtentSparkReporter("./src/test/resources/Reports/Report.html");
		objReport=new ExtentReports();
		objReport.attachReporter(objReporter);
	}

	public void onFinish(ISuite suite) {
		// TODO Auto-generated method stub
		logln("Suite END");
		objReport.flush();
	}

}
