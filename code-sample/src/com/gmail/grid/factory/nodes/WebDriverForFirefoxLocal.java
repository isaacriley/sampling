/**
 * 
 */
package com.gmail.grid.factory.nodes;

import java.net.MalformedURLException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import com.gmail.grid.factory.WebDriverForNode;

/**
 * Class (part of the factory pattern) creates a WebDriver that's capable of perusing Node that houses a Firefox 
 * browser (local machine).
 * @author IRiley
 */
public class WebDriverForFirefoxLocal implements WebDriverForNode {
	
	@Override
	public WebDriver getWebDriver() throws MalformedURLException{
				
		//keep it simple		
		FirefoxOptions options = new FirefoxOptions();
		 return new FirefoxDriver(options);
	}

}
