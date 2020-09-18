package Common;

//WebDriver related packages
//import org.openqa.selenium.WebDriver;								//WebDriver
//import org.openqa.selenium.chrome.ChromeDriver;						//ChromeDriver
//import org.openqa.selenium.firefox.FirefoxDriver;					//FirefoxDriver
//File handling packages
import java.io.File;												//File
import java.io.FileInputStream;										//File Input Stream
import java.io.IOException;											//IOException
//Excel handling packages
import org.apache.poi.xssf.usermodel.XSSFWorkbook;					//HSSFWorkbook for .xlsx
import org.apache.poi.ss.usermodel.Workbook;						//Workbook
import org.apache.poi.ss.usermodel.Sheet;							//Sheet
import org.apache.poi.ss.usermodel.Row;								//Row
import org.apache.poi.ss.usermodel.Cell;							//Cell
import org.apache.poi.ss.usermodel.CellType;						//CellType
//Java utilities for handling lists
import java.util.List;												//List
import java.util.ArrayList;											//ArrayList

/***************************************************************************************************
 * Class Name: Helper Class
 * Description:
 * 		Provides various static methods to aid Selenium automation process.
 * Members:
 * 		strChromeDriverPath		- holds relative path to Chrome Driver
 * 		strGeckoDriverPath		- holds relative path to Gecko Driver
 * 		isLoggingEnabled		- looked up by log and logln functions before printing messages
 * 		objDataWorkbook			- holds Excel workbook that has data
 * Methods:
 * 		setDataWorkbook()		- Opens workbook at location specified and assigns to 
 * 								  objDataWorkbook
 * 		closeDataWorkbook()		- Closes workbook held by objDataWorkbook
 * 		getDataFromWorkook()	- Returns data present in sheet specified by strSheetName
 * 		getDataFromSheet()		- Returns data present on objDataSheet object passed as parameter
 * 		getDataInArray()		- Converts List formatted data to array format
 * 		getWebDriver()			- returns WebDriver of requested browser(strBrowserName)
 * 		getChromeDriver()		- returns ChromeDriver Object(private)
 * 		getWebDriver()			- returns requested web driver
 * 		enableLogging()			- Enables logging. isLoggingEnabled=true
 * 		disableLogging()		- Disables logging. isLoggingEnabled=false
 * 		log()					- Prints string strLogMessage to system out
 * 		logln()					- Prints string strLogMessage to system out with newline character
 **************************************************************************************************/
public class HelperClass {
	//private final static String strChromeDriverPath=
		//	"./src/test/resources/WebDrivers/chromedriver.exe";
	//private final static String strGeckoDriverPath=
		//	"./src/test/resources/WebDrivers/geckodriver.exe";
	private static Boolean isLoggingEnabled=false;
	private static Workbook objDataWorkbook=null;
	
	/***********************************************************************************************
	 * Method Name: setDataWorkbook
	 * Description:
	 * 		Sets objDataWorkBook object in class to workbook present at location indicated by
	 * parameter strDataPath.
	 * Design:
	 * 	- Open File and FileInputStream for file located at strTestDataPath
	 *  - Create Workbook object.
	 *  - In case of IOException, do nothing. Workbook object is already null.
	 * Parameters:
	 * 		String strDataPath		- holds relative path to test data sheet
	 * Returns: Nothing
	 * Raises: Nothing
	 **********************************************************************************************/
	public static void setDataWorkbook(String strDataPath) {
		try {
			//Open File and FileInputStream for file located at strTestDataPath
			File objDataFile=new File(strDataPath);
			FileInputStream objDataFileInputStream=new FileInputStream(objDataFile);
			//Create Workbook object.
			objDataWorkbook=new XSSFWorkbook(objDataFileInputStream);
			objDataFileInputStream.close();
		}
		catch(IOException e) {
			//In case of IOException, do nothing. Workbook object is already null.
			//Do Nothing
		}
	}
	
	/***********************************************************************************************
	 * Method Name: closeDataWorkbook
	 * Description:
	 * 		Closes objDataWorkBook object in class.
	 * Design:
	 * 	- Close objDataWorkBook
	 * Parameters: None
	 * Returns: Nothing
	 * Raises: Nothing
	 **********************************************************************************************/
	public static void closeDataWorkbook() {
		try {
			//Close objDataWorkBook
			objDataWorkbook.close();
		}
		catch(IOException e) {
			//Do nothing
		}
	}
	
	/***********************************************************************************************
	 * Method Name: getDataFromWorkook
	 * Description:
	 * 		Extracts data from excel sheet named strSheetName in excel Workbook held in 
	 * objTestDataWorkbook. Data is returned in 2D array of object
	 * Design:
	 * 	- Initialize return object array
	 *  - Create Worksheet object based on sheet name strSheetName
	 *  - Call getDataFromSheet to get data and assign to return object array
	 * 	- Return object array
	 * Parameters:
	 * 		String strSheetName			- holds test data sheet name
	 * Returns: Object[][]
	 * Raises: Nothing
	 **********************************************************************************************/
	public static Object[][] getDataFromWorkbook(String strSheetName) {
		//Initialize return object array
		Object[][] objData=null;
		//Create Worksheet object based on sheet name strSheetName
		Sheet objDataSheet=objDataWorkbook.getSheet(strSheetName);
		//Call getDataFromSheet to get data and assign to return object array
		objData=getDataFromSheet(objDataSheet);
		//Return object array		
		return objData;
	}
	
	/***********************************************************************************************
	 * Method Name: getDataFromSheet
	 * Description:
	 * 		Extracts data from excel sheet object passed as a paraeter. Data is returned in 2D
	 * array of object
	 * Design:
	 * 	- Initialize return object to null
	 * 	- initialize TestDataList
	 * 	- For each Row element,
	 * 		if row number is 0, skip the same as it is header row
	 * 		extract row element
	 * 		initialize RowDataList
	 * 		for each Cell element,
	 * 			if cell is blank or null, set cell value to ""
	 * 			else, set cell value to actual value
	 * 			add cell value to RowDataList
	 * 		add RowDataList to TestDataList
	 * 	- Get Test data in Object[][] format. Call getDataInArray and assign to return object
	 * 	- Return object array
	 * Parameters:
	 * 		Sheet objWorksheet	-	holds Worksheet object that has test data
	 * Returns: Object[][]
	 * Raises: Nothing
	 **********************************************************************************************/
	private static Object[][] getDataFromSheet(Sheet objDataSheet) {
		//Initialize return object to null
		Object[][] objData=null;
		//initialize DataList
		List<List<String>> lstData=new ArrayList<List<String>>();
		//For each Row element,
		for (Row objRow:objDataSheet) {
			//if row number is 0, skip the same as it is header row
			if (objRow.getRowNum()==0) {
				continue;
			}
			//initialize RowDataList
			List<String> lstRow=new ArrayList<String>();
			//for each Cell element,
			for (Cell objCell:objRow) {
				String strCellValue=null;
				//if cell is blank or null, set cell value to ""
				if (objCell==null || objCell.getCellType()==CellType.BLANK) {
					strCellValue="";
				}
				//else, set cell value to actual value
				else {
					strCellValue=objCell.getStringCellValue();
				}
				//add cell value to RowDataList
				lstRow.add(strCellValue);
			}
			//add RowDataList to TestDataList
			lstData.add(lstRow);
		}
		//Get Test data in Object[][] format. Call getDataInArray and assign to return object
		objData=getDataInArray(lstData);
		//Return object array
		return objData;
	}
	
	/***********************************************************************************************
	 * Method Name: getDataInArray
	 * Description:
	 * 		Converts test data received in List format and returns the same in Array format.
	 * Received data has format List<List<String>>. Each cell value(String) in a row is present in 
	 * List<String>. Such lists are added to another List, which is received as a parameter.
	 * Return data is in Object[][] 2D array format
	 * Design:
	 * 	- Initialize return object with length of test data list
	 * 	- For each RowList in DataList.
	 * 		for each data CellValue in RowList.
	 * 			Assign value to corresponding testData[][]
	 * 	- Return test data
	 * Parameters:
	 * 		List<List<String>>	-	Each cell value(String) in a row is present in 
	 * 								List<String>. Such lists are added to another List
	 * Returns: Object[][]
	 * Raises: Nothing
	 **********************************************************************************************/
	private static Object[][] getDataInArray(List<List<String>> lstData) {
		//Initialize return object with length of test data list
		int iRowCount=lstData.size();			//get row size
		int iCellCount=lstData.get(0).size();	//get cell size of first row
		Object[][] objData=new Object[iRowCount][iCellCount];
		//For each RowList in DataList
		for (int iRow=0;iRow<iRowCount;iRow++) {
			//for each data CellValue in RowList
			for (int iCell=0;iCell<iCellCount;iCell++) {
				//Assign value to corresponding testData[][]
				objData[iRow][iCell]=lstData.get(iRow).get(iCell);
			}
		}
		//Return test data
		return objData;
	}
	
	/***********************************************************************************************
	 * Method Name: getWebDriver
	 * Description:
	 * 		Returns instance of WebDriver. Based on requested browser, call is made to method
	 * corresponding to the same browser to receive WebDriver created for browser. Currently handles
	 * Browsers- Chrome and InternetExplorer. Returns 'null' if browser is not included.
	 * Design:
	 * 	- Based on strBrowserName, call corresponding method to receive WebDriver
	 * 		for 'Chrome', chromedriver
	 * 		for 'Firefox', FirefoxDriver
	 * 	- Return the WebDriver
	 * Parameters:
	 * 		String strBrowserName		- holds name of the browser.
	 * Returns: WebDriver - holds newly created instance of WebDriver
	 * Raises: Nothing
	 **********************************************************************************************/
	/*
	public static WebDriver getDriver(String strBrowserName) {
		WebDriver objWebDriver=null;
		//Based on strBrowserName, call corresponding method to receive WebDriver
		//for 'Chrome', chromedriver
		if (strBrowserName.equalsIgnoreCase("Chrome")) {
			objWebDriver=getChromeDriver();
		}
		//for 'Firefox', FirefoxDriver
		else if(strBrowserName.equalsIgnoreCase("Firefox")) {
			objWebDriver=getFirefoxDriver();						
		}
		//Return the WebDriver
		return objWebDriver;
	}
	*/
	/***********************************************************************************************
	 * Method Name: getChromeDriver
	 * Description:
	 * 		Returns instance of Chrome driver. This is a private method, used by public method
	 * getDriver.
	 * Design:
	 * 	- Set 'webdriver.chrome.driver' property to relative location of Chrome driver
	 * 	- Return instance of ChromeDriver
	 * Parameters: None
	 * Returns: WebDriver - holds newly created instance of ChromeDriver
	 * Raises: Nothing
	 **********************************************************************************************/
	/*
	private static WebDriver getChromeDriver() {
		//Set 'webdriver.chrome.driver' property to relative location of Chrome driver
		System.setProperty("webdriver.chrome.driver", strChromeDriverPath);
		System.setProperty("webdriver.chrome.silentOutput", "true");
		//Return instance of ChromeDriver
		return new ChromeDriver();
	}
	*/
	/***********************************************************************************************
	 * Method Name: getFirefoxDriver
	 * Description:
	 * 		Returns instance of Firefox driver. This is a private method, used by public method
	 * getDriver.
	 * Design:
	 * 	- Set 'webdriver.gecko.driver' property to relative location of IE driver
	 * 	- Return instance of FirefoxDriver
	 * Parameters: None
	 * Returns: WebDriver - holds newly created instance of InternetExplorerDriver
	 * Raises: Nothing
	 **********************************************************************************************/
	/*
	private static WebDriver getFirefoxDriver() {
		//Set 'webdriver.gecko.driver' property to relative location of IE driver
		System.setProperty("webdriver.gecko.driver", strGeckoDriverPath);
		System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"null");
		//Return instance of FirefoxDriver
		return new FirefoxDriver();
	}
	*/
	
	/***********************************************************************************************
	 * Method Name: enableLogging
	 * Description:
	 * 		Sets isLoggingEnabled to true.
	 * Design:
	 * 	- Set isLoggingEnabled to true
	 * Parameters: None
	 * Returns: Nothing
	 * Raises: Nothing
	 **********************************************************************************************/
	public static void enableLogging() {
		//Set isLoggingEnabled to true
		isLoggingEnabled=true;
	}
	
	/***********************************************************************************************
	 * Method Name: disableLogging
	 * Description:
	 * 		Sets isLoggingEnabled to false.
	 * Design:
	 * 	- Set isLoggingEnabled to false
	 * Parameters: None
	 * Returns: Nothing
	 * Raises: Nothing
	 **********************************************************************************************/
	public static void disableLogging() {
		//Set isLoggingEnabled to false
		isLoggingEnabled=false;
	}
	
	/***********************************************************************************************
	 * Method Name: log
	 * Description:
	 * 		Prints message to Standard system out without newline character.
	 * Design:
	 * 	- If logging is enabled,
	 * 		User System out to print message without newline character
	 * Parameters: strLogMessage
	 * Returns: Nothing
	 * Raises: Nothing
	 **********************************************************************************************/
	public static void log(String strLogMessage) {
		//If logging is enabled,
		// 		User System out to print message without newline character
		if (isLoggingEnabled) {
			System.out.print(strLogMessage);
		}
	}
	
	/***********************************************************************************************
	 * Method Name: logln
	 * Description:
	 * 		Prints message to Standard system out with newline character.
	 * Design:
	 * 	- If logging is enabled,
	 * 		User System out to print message with newline character
	 * Parameters: strLogMessage
	 * Returns: Nothing
	 * Raises: Nothing
	 **********************************************************************************************/
	public static void logln(String strLogMessage) {
		//If logging is enabled,
		// 		User System out to print message with newline character
		if (isLoggingEnabled) {
			System.out.println(strLogMessage);
		}
	}
}
