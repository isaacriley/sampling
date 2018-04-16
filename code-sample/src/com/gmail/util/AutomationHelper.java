/**
 * 
 */
package com.gmail.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;

/**
 * A utility class to help with sundry test automation needs
 * @author IRiley
 *
 */
public final class AutomationHelper {

	private AutomationHelper(){}
	
	private static final Logger log = Logger.getLogger(AutomationHelper.class.getName());
	private static String osName="os.name";
	private static String osArchitecture="os.arch";
	private static String osVersion="os.version";	
	
	
	/**
	 * Accessor method that gets the name of the operating system 
	 * @return the name of the host operating system
	 */
	public static final String getOperatingSystemName(){
		//you say a String is final ... meh... to be refactored
		return System.getProperty(osName);		
	}
	/**
	 * Accessor method that gets the architecture of the operating system (ex. AMD 64) 
	 * @return the architecture of the host operating system
	 */
	public static final String getOperatingSystemArch(){
		return System.getProperty(osArchitecture);
	}
	
	/**
	 * Accessor method that gets the version of the operating system 
	 * @return the version of the host operating system
	 */
	public static final String getOperatingSystemVersion(){
		return System.getProperty(osVersion);		
	}
	
	/**
	 * Checks to ensure that we on the correct platform to run Safari tests
	 * @return true if we're on a MAC or a Windows box
	 */
	public static boolean isSafariSupportedPlatform() {
	    Platform current = Platform.getCurrent();
	    return Platform.MAC.is(current) || Platform.WINDOWS.is(current);
	  }
	
	
	/**
	 *  Writes an entry to the logs. The Throwable object is captured and its stack trace
	 * @param event the event you want to log
	 * @param t the Throwable object along with its stack trace
	 */
	public static void logExceptionAndThrowable(String event, Throwable t){
		log.info("\n*** "+event+" ***", t);
	}
	
	/**
	 * Writes an entry to the logs as is (no-formatting)
	 * @param string String to write to logs
	 */
	public static void writeToLogsAsIs(String string) {
		log.info(string);	
	}
	
	/**
	 *  Writes a stack trace entry to the log
	 * @param stackTrace the event you want to log
	 */
	public static void logStackTrace(String stackTrace){
		log.info("\n*** "+stackTrace+" ***\n");
	}

	/**
	 * Utility method that generates pseudo-random numbers based on the seed
	 * @param seed seed for returning random number
	 * @return random number as a string
	 */
	public static int getRandomNumberAsInt(long seed) {
		Random rand = new Random(seed);
		return rand.nextInt();
	}
	
	/**
	 * Overloaded helper method returns a pseudo-random number as a string (0, 1,000,000]
	 * @return random number as a string
	 */
	public static String getRandomNumAsString(){
		String numAsString="";
		Random rand = new Random();
		int num = rand.nextInt(1000000);
	 	return numAsString+Integer.toString(num);
	}
	
	/**
	 * Generates a pseudo random (0, 1,000,000]
	 * @return a pseudo random (0, 1,000,000]
	 */
	public static int getRandomNumberInt(){
		Random rand = new Random();
		return rand.nextInt(1000000);
	}
	
	/**
	 * Generates a pseudo random (0, seed]
	 * @param seed integer range for pseudo-random generation
	 * @return a pseudo random (0, seed]
	 */
	public static int getRandomNumberInt(int seed){
		Random rand = new Random();
		return rand.nextInt(seed);
	}
	
	/**
	 * Parse a CSV file; using a comma (',') as the delimiter
	 * @param pathToCsvFile path to CSV file
	 * @return reference Scanner object that breaks up the CVS file into tokens
	 */
	// @TODO: replace with utility class that handles the scanning of Excel (and CSV) files
	public static Scanner readCSV(String pathToCsvFile){
		String DELIMITER =",";
		Scanner scan=null;
		try {
			scan = new Scanner(new File(pathToCsvFile));
		} catch (FileNotFoundException e) {
			AutomationHelper.logExceptionAndThrowable("File Not Found!!\n"+e.getMessage(), e);
		}//catch
		scan.useDelimiter(DELIMITER);
		scan.close();
		return scan; 
	}//readCSV

	/**
	 * For test purposes to enter strings in the app; useful in text areas
	 * @return Formatted text that fits in a TextArea
	 */
	public static String multiLineRandomizedString(){
		return "Entered by Selenium/WebDriver: \n"
				+ "["+getDateTimezone()+"]\n"
						+ "** "+getOperatingSystemArch()+" "+getOperatingSystemName()
					+" "+getOperatingSystemVersion()+" **";
	}
	
	/**
	 * For test purposes to enter strings in the app's text fields
	 * @return Formatted text that fits in a TextArea
	 */
	public static String randomizedString(){
		return "Entered by Selenium/WebDriver "+ "["+getDateTimezone()+"]";
	}
	
	/**
	 * Utility method returns the time as a String; format is -- hour:mins:secs:tenth of seconds
	 * @return formatted date of "hour:mins:secs:tenth of seconds" format
	 */
	public static String timeOnlyTimeStampAsString() {
		DateFormat dateFormat = new SimpleDateFormat("k:mm:ss:SS");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	/**
	 * Helper to provide a formatted Date/Time string for testing purposes
	 * @return: formatted string of type "MMM dd h:mm:ss am/pm timezone"
	 */
	public static String getDateTimezone() {
		DateFormat dateFormat = 
						new SimpleDateFormat("E, MMM dd h:mm:ss a '('zzzz')'");
		//("yyyy/MM/dd HH:mm:ss a");
		Date date = new Date();
		return dateFormat.format(date);
	}
		
	/**
	 * Helper to provide a formatted Time string for testing purposes
	 * @return: formatted string of type yyyy-mm-dd [hour-min-sec]
	 */
	public static final String getTimeStampWithDashes(){
	
		SimpleDateFormat date= new SimpleDateFormat("yyyy-MM-dd [hh-mm-ss]");
		return date.format(new Date());
	}
	
	/**
	 * Helper method to provide a timestamp of the format: yyyymmddhhmms;
	 * i.e. 4-digit year,2-digit month, 2-digit day etc... ex. 201608301205123
	 * "year.month.day.hourMinuteSecond"
	 * @return formatted string of type "yyyymmddhhmms"
	 */
	public static final String getTimestamp(){
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmms");
		return date.format(new Date());
	}
	
	/**
	 * Asserts whether we're on a platform that we purport to support
	 * @return true if we're on a Windows or MAC 
	 */
	public static boolean isSupportedPlatform() {
	    Platform current = Platform.getCurrent();
	    return Platform.MAC.is(current) || Platform.WINDOWS.is(current);
	  }

	
	/**
	 * Checks if the number is even
	 * @param num number to check
	 * @return true if even (and by implication, false if odd)
	 */
	public static boolean isEven(int num) {
		return (num %2) ==0;
	}
	
	
	/**
	 * Helper method to execute .bat and .cmd scripts.
	 * Ex. one could use this method to kill the stray GeckoDrivers that will interfere with the F5  so-called
	 * login page 
	 * @param batScriptLocation the location of the .bat or .cmd script
	 */
	public static void killStrayDrivers(String batScriptLocation) {
		try {
			Runtime.getRuntime().exec("cmd /c start "+batScriptLocation);
		} catch (Exception e) {
			AutomationHelper.logStackTrace("Failed to Kill Driver(s)\n"+e.getMessage());
			e.printStackTrace();
		}
	}
	
}
