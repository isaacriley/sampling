/**
 * 
 */
package com.gmail.grid.factory;

import com.gmail.constants.GridConstants;
import com.gmail.grid.factory.nodes.WebDriverForFirefoxLocal;
import com.gmail.grid.factory.nodes.WebDriverForLocalChromeBrowser;

/**
  * This  factory for the Factory Method code design pattern 
 * Based on the configuration string, we provide a WebDriver that's capable of perusing the browser/app of the target
 * @author IRiley
 *
 */
public final class NodesFactory {

	public static WebDriverForNode getWebDriverForGrid(String criterion) {
		
		if(criterion.equalsIgnoreCase(GridConstants.CHROME_NODE_LOCAL)){
			return new WebDriverForLocalChromeBrowser();
		}//a Chrome box/VM target node
		else if(criterion.equalsIgnoreCase(GridConstants.FIREFOX_NODE_LOCAL)){
			return new WebDriverForFirefoxLocal();
		}//a Firefox browser, target node
		/*
		* More requests for WebDriver objects for other targets --  web, mobile web, native mobile
		* NB: Appium implements the WebDriver interface so this factory method can be one stop shop for all
		* types of targets, web, mobile web & native mobile ... the beauty of coding to interfaces
		*/
		return null;//if fail return null	
	}

}
