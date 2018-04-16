/**
 * 
 */
package com.gmail.test;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;

import com.gmail.util.MOVtoMP4Converter;
import com.gmail.constants.GridConstants;
import com.gmail.constants.TestConstants;
import com.gmail.data.PropertiesFileHandler;
import com.gmail.grid.GridParallelHelper;
import com.gmail.grid.GridPrep;
import com.gmail.pageObjects.BasePage;
import com.gmail.pageObjects.GoogleLandingPage;
import com.gmail.pageObjects.mailbox.AccountDropdown;
import com.gmail.pageObjects.mailbox.GmailMailbox;
import com.gmail.pageObjects.signin.CreateAccountPage;
import com.gmail.pageObjects.signin.PasswordPage;
import com.gmail.pageObjects.signin.UsernamePage;
import com.gmail.test.testPrep.JUnitTestEnvironmentsParameters;
import com.gmail.util.AutomationHelper;
import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;

/**
 *This is the parent/base class for all test cases
 * @author IRiley
 *
 */
@RunWith(GridParallelHelper.class)
public class BaseTestCase extends TestWatcher{
	
	private String url = PropertiesFileHandler.getValue("url", TestConstants.PATH_TO_LOGIN_PROPERTIES_FILE);
	
	protected String environment;
	protected WebDriver driver;
	protected static String testCaseName;
	private static ATUTestRecorder recorder;
	private static boolean isRecording = false;
	private static String recordedVideoFilename;
	private UsernamePage uname;
	private PasswordPage pwd;
	private CreateAccountPage create;
	private GmailMailbox mail;
	
	@Rule
	public TestWatcher testWatcher = new TestWatcher() {
	    @Override
	    public void starting(Description desc) {
	    	AutomationHelper.writeToLogsAsIs("*** Executing: "+desc.getDisplayName()
	    				+" ... Environment{"+environment+"}\n");
	    }

	    @Override
	    public void finished(Description desc) {
	    	AutomationHelper.writeToLogsAsIs("*** End Execution: "+desc.getDisplayName()
				+" ... Environment{"+environment+"}\n");
	    	driver.manage().deleteAllCookies();
	    	driver.quit();
	    }

	    /*
	     * (non-Javadoc)
	     * @see org.junit.rules.TestWatcher#failed(java.lang.Throwable, org.junit.runner.Description)
	     */
	    @Override
	    public void failed(Throwable e, Description d) {
	    	 
	    	//String scrFilename = "failed_"+d.getClassName()+"_"+d.getMethodName()
	    	String scrFilename = d.getMethodName()+"_"+AutomationHelper.getTimestamp()+".png";
	    	BasePage.takeAScreenshot(driver,GridConstants.SCREENSHOT_PATH+scrFilename);
	    	AutomationHelper.writeToLogsAsIs("Test Failure: "+d.getMethodName());
	    	AutomationHelper.writeToLogsAsIs("Screenshot saved in "+TestConstants.SCREENSHOT_DIR_WINDOWS);
	    	AutomationHelper.logExceptionAndThrowable(e.getMessage(), e);
	    }
	};

	private AccountDropdown accountdropdown;

	
	
	/**
	 * In order to properly parameterize the test cases and take full advantage 
	 * of the grid, we pass each environment string to the constructor of the
	 * test case -- which in turns feeds that to Parameterized.class 
	 * @param environment test environment parameter
	 */
	public BaseTestCase(String environment){
		this.environment = environment;	
	}
	
	@Parameters
	public static Collection<Object[]> initEnvironments(){
		//parameters
		return JUnitTestEnvironmentsParameters.initNodesForGrid();	
	}
	
	/*
	 * Sets up the world so that it is ready for testing: allows for test cases to be record themselves while 
	 * they're being executed; if a mobile test suite is being run, we start and shutdown the emulator and appium server
	 */
	@BeforeClass
	public static void setupTestingWorld() {
		//won't start automatically, will let config decide
		String startRecording = PropertiesFileHandler.getValue("record", TestConstants.MISCELLANOUS_CONFIG_FILE);
		if(startRecording.contains("yes")) {
			String filename ="video_"+AutomationHelper.getTimestamp();
			try {
				recorder = new ATUTestRecorder(TestConstants.RECORDED_TESTS_DIR_WINDOWS,filename,false);
				recorder.start();
			} catch (ATUTestRecorderException e) {
				AutomationHelper.logExceptionAndThrowable("Recording Failure "+e.getMessage(), e);
				e.printStackTrace();
			}//catch
		}//if start

	}
	
	/*
	 * This is a pre-test case setup; the @BeforeClass method applies to the pre-test suite scenario
	 */
	@Before
	public void setup(){
		//get the appropriate WebDriver instance
		driver = GridPrep.getRemoteWebDriverForNode(environment);
		assertNotNull(driver);
		AutomationHelper.logStackTrace("Created a WebDriver for: "+environment);
	}

	
	/*
	 * Captures and prints the Console log to the log file
	 */
	protected void printConsoleLog(WebDriver driver, String type) {
	    List<LogEntry> entries = driver.manage().logs().get(type).getAll();
	    AutomationHelper.logStackTrace(" Console Log Start ["+environment+"]");
	    				//entries.size() + " " + type);
	    for (LogEntry entry : entries) {
	      AutomationHelper.logStackTrace(new Date(entry.getTimestamp()) + " " 
	    		  			+ entry.getLevel() + " " + entry.getMessage());
	    }
	    AutomationHelper.logStackTrace(" Console Log End ["+environment+"] ");
	}
	
	/*
	 * This applies to the post test case scenario (not post test suite)
	 */
	@After
	public void teardown(){
		if(driver !=null){
			//The nodes that don't support console logging
			if((environment.compareToIgnoreCase(GridConstants.IE11_NODE_REMOTE)==0) ||
					(environment.compareToIgnoreCase(GridConstants.IE11_NODE_LOCAL)==0)||
				(environment.compareToIgnoreCase(GridConstants.EDGE_NODE_LOCAL)==0)||
				(environment.compareToIgnoreCase(GridConstants.NATIVE_ANDROID_LOLLIPOP_NODE)==0)||
				(environment.compareTo(GridConstants.IPHONE5_NODE)==0)||
				(environment.compareTo(GridConstants.IPHONE6_NODE)==0)||
				(environment.compareTo(GridConstants.FIREFOX_NODE_LOCAL)==0))
			{
				AutomationHelper.logStackTrace("The Console logging feature is incompatible with  "+environment);
			}
			else{
				try {
				      Logs logs = driver.manage().logs();
				      AutomationHelper.logStackTrace("Log types: "+	logs.getAvailableLogTypes());
				      printConsoleLog(driver, LogType.BROWSER);
				    } finally {				    	
				    	//if test watcher not being used close driver here
				    }//finally
			}//else
		//if test watcher not used, quit driver here
		
		}//if !null
		
	}//method
	
	/*
	 * As a best practice, clean up after one's self: where applicable shutdown mobile emulators and servers; shutdown 
	 * the video recorder if the test cases were being recorded
	 */
	@AfterClass
	public static void restoreWorldToPreviousState(){
		//AutomationHelper.killStrayGeckodrivers_WINDOWS();
		if(isRecording){
			try {
				recorder.stop();
				AutomationHelper.writeToLogsAsIs("Compressing "+recordedVideoFilename);
				MOVtoMP4Converter.convertMovToMp4_ffmpeg_windows(recordedVideoFilename+".mov", recordedVideoFilename);
			}catch (ATUTestRecorderException e) {
				AutomationHelper.logExceptionAndThrowable("Couldn't Stop Test Recording**\n"+e.getMessage(),e);
				e.printStackTrace();
			}//catch
		}//if recording
	}
	
	/**
	* All test cases entering the app via GMail button will use this method
	*/
	protected GmailMailbox loginViaGmailButton(WebDriver driver, String username, String password) {
		
		GoogleLandingPage land = new GoogleLandingPage(driver);
		
		land.gotoHome(driver, url);
		create = land.clickGmailButton(driver);
		uname = create.clickSignInButton(driver);
		uname.enterUsername(driver, username);
		pwd = uname.clickNextButton(driver);
		pwd.enterPassword(driver, password);
		mail = pwd.nextWhenGmailClickedFirst(driver);
		return mail;
		
	}
	
	protected void logout(WebDriver driver) {
		accountdropdown = mail.clickAccountButton(driver);
		accountdropdown.signout(driver);
	}
	
	/**
	 * There are times when there isn't any data to test with and other times, we
	 * just want to halt everything and move to the next test
	 */
	protected void abortTest() {
		String scrFilename = 
    			"aborted_"+AutomationHelper.getTimestamp()+".png";
    	BasePage.takeAScreenshot(driver, GridConstants.SCREENSHOT_PATH+scrFilename);
		driver.close();
		System.exit(0);
	}
}
