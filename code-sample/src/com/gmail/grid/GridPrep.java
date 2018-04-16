/**
 * 
 */
package com.gmail.grid;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

import com.gmail.grid.factory.NodesFactory;
import com.gmail.grid.factory.WebDriverForNode;
import com.gmail.util.AutomationHelper;

/**
 * In order to get the grid properly utilized, we need to do some prep work (for example getting the correct 
 * WebDriver object from the factory).
 * @author IRiley
 *
 */
public final class GridPrep {

	private GridPrep() {}
	
	/**
	 * Fetches a WebDriver object (a RemoteDriver for remote URLs) that is compliant with the target device 
	 * @param criterion the target at which to run the test cases (ex. Edge  browser on Windows 10)
	 * @return the correct/appropriate WebDriver instance for the node; else null if the node couldn't be found 
	 */
	public static WebDriver getRemoteWebDriverForNode(String criterion){
		
		WebDriverForNode nodeWebDriver = 
				NodesFactory.getWebDriverForGrid(criterion);
		try {
			return nodeWebDriver.getWebDriver();
		} catch (MalformedURLException e) {
			AutomationHelper.logExceptionAndThrowable("Fatal: Please check the URL of the node ["+criterion+"]", e);
			e.printStackTrace();
		}
		return null;
	}

}
