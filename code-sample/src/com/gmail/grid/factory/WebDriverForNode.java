/**
 * 
 */
package com.gmail.grid.factory;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

/**
 * As a necessary component of the Factory Pattern,this is the interface for creating objects (the "subclasses" does 
 * the actual instantiation) 
 * @author IRiley
 *
 */
public interface WebDriverForNode {

	public WebDriver getWebDriver()throws MalformedURLException;
}
