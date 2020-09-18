package Common;
import Common.HelperClass;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class TestStub_HelperClass {
	public static void main(String[] args) {
		HelperClass.log("This will not print");
		HelperClass.enableLogging();	
		//testDataTest();
		reportTest();
	}
	
	private static void testDataTest() {
		String strTestDataPath="./src/test/resources/TestData/TestData.xlsx";
		String strSheetName="validateInvalidAuthorization";
		HelperClass.setDataWorkbook(strTestDataPath);
		Object[][] objTestData=HelperClass.getDataFromWorkbook(strSheetName);
		HelperClass.closeDataWorkbook();
		for (Object[] objTestDataRow:objTestData) {
			for(Object objTestDataCell:objTestDataRow) {
				HelperClass.log((String)objTestDataCell);
			}
			HelperClass.logln("");
		}
		//HelperClass.writeData("sampleData","Hi value",2);
		HelperClass.closeDataWorkbook();
	}
	
	private static void reportTest() {
		ExtentSparkReporter objReporter=new ExtentSparkReporter("./src/test/resources/Reports/sample.html");
		ExtentReports objReport=new ExtentReports();
		objReport.attachReporter(objReporter);
		objReport.setAnalysisStrategy(AnalysisStrategy.TEST);
		objReport.createTest("Test#1")
				.createNode("Data1")
				.pass("Passed")
				.createNode("Data2")
				.pass("Psssed");
		objReport.flush();
	}
}
