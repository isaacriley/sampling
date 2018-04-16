/**
 * 
 */
package com.gmail.grid.factory.nodes;

import java.net.MalformedURLException;
import java.util.logging.Level;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.gmail.grid.factory.WebDriverForNode;

/**
 * Class (part of the factory pattern) creates a WebDriver that's capable of perusing Node that houses a Chrome
 * browser (local machine)
 *@author IRiley
 */
public class WebDriverForLocalChromeBrowser implements WebDriverForNode {

	@Override
	public WebDriver getWebDriver() throws MalformedURLException {
				
		ChromeDriverService service = new ChromeDriverService.Builder()
              //  .usingDriverExecutable(new File("/usr/local/chromedriver")) ... moot with executable of the $PATH
                .usingAnyFreePort()
                .build();
		ChromeOptions options = new ChromeOptions();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		options.merge(capabilities);    
		return new ChromeDriver(service, options);
	}

}
