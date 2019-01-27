/**
 * 
 */
package com.gmail.pageObjects;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.ScreenshotException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gmail.util.AutomationHelper;

/**
 * <pre>This class forms the basis for the Page Object Model (POM) pattern</pre>
 * This is the base class for the Page Object Model. Each page and its own accompanying page object 
 * will inevitably inherit/extend this base class.
 *
 * I have omitted some of the code, this can be a rather sizeable class -- you'd be surprised 
 * what the individual page objects have in common.
 * 
 * @author IRiley
 *
 */
public abstract class BasePage {
	
	public Object NOBODY ="Widget is not present";
	
	protected WebDriver driver;
	//assuming that each page has its own unique title
	private String pageTitle;
	protected String URL;
	/*
	 * How long to wait for widget, we chose 75 seconds -- Google recommends  having a timeout that is greater 
	 * than the socket timeout of 45 seconds. 
	 */
	private long WAIT_TIME =75; 
	private long POLL_TIME =5;
	private static final long IMPL_WAIT = 10; //10 seconds
	
	/**
	 * Each page object has its own WebDriver (for the purposes of manipulating and integrating with itself)
	 * @param driver crawling the page
	 */
	public BasePage(WebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * If the page's title is unique, you may use this constructor
	 * @param driver crawling the page
	 * @param pageTitle the page's title
	 */
	public BasePage(WebDriver driver, String pageTitle){
		this.driver = driver;
		this.pageTitle = pageTitle;
	}
	
	/**
	 * We allow each page object to decide on how it wants to open itself 
	 * @param driver crawling the page
	 */
	public abstract void openPage(WebDriver driver);
	
	/**
	 * Each page object has the ability to either open its own page or another page for that matter
	 * @param driver crawling the page
	 * @param url URL to open
	 * @return reference to the {@link WebDriver} that will be used at the target
	 */
	protected WebDriver openURL(WebDriver driver, String url){
		try{
			driver.get(url);
			return driver;
		}catch(Exception e){
			AutomationHelper.logExceptionAndThrowable(e.getMessage(), e);
		}
		return driver;//give back driver as is
	}
	
		
	/**
	 * Wait for element/widget to be present (in the DOM)
	  * @param driver WebDriver that's crawling the page
	 * @param by the selector (XPath or CSS) to get at the widget
	 * @return reference to the {@link WebElement} widget , or null if there is no reference to a widget
	 * <p>
	 * 	 Note: I could have used a Null object design pattern or throw an exception but I'm simplifying the
	 * 		   method's usage. Besides, we want the test case to fail immediately if the widget isn't found.
	 * </p>
	 */
	protected WebElement waitForPresenceOfWidget(WebDriver driver, By by){
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME, POLL_TIME);
		try{
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
			return element;
		}catch(Exception e){
			AutomationHelper.writeToLogsAsIs("\n***Failed to interact with Widget identified by"
					+ "\n\tSelector: "+by.toString()+"***");
			AutomationHelper.logExceptionAndThrowable(e.getMessage(), e);
		}//catch
		return null; //if all fails
	}
	
	/**
	 * Find the widget on the page
	 * @param driver crawling the page
	 * @param by the XPATH or CSS locator to get to the widget
	 * @return reference to the {@link WebElement} widget itself, or null if there is no reference to a widget
	 * <p>
	 * 	 Note: I could have used a Null object design pattern or throw an exception but I'm simplifying the
	 * 		   method's usage. Besides, we want the test case to fail immediately if the widget isn't found.
	 * </p>
	 */
	public WebElement findElement(WebDriver driver, By by){
		try{
			waitForPresenceOfWidget(driver, by);
			return driver.findElement(by);
		}catch(Exception e){
			AutomationHelper.writeToLogsAsIs("\n***Failed to interact with Widget identified by"
					+ "\n\tSelector: "+by.toString()+"***");
			AutomationHelper.logExceptionAndThrowable(e.getMessage(), e);
		}//catch
		return null;
	}//findElement

	

	/**
	 * Method facilitates clicking a widget
	 * @param driver crawling the page
	 * @param selector CSS or XPath to the widget that's being clicked
	 */
	protected void clickElement(WebDriver driver, By selector){
		try{
			if(isElementPresent(driver, selector)){
				highlightElement(driver,driver.findElement(selector));
				AutomationHelper.writeToLogsAsIs("Clicking Widget: "+selector.toString());
				driver.findElement(selector).click();
			}//if present
		}catch(Exception e){	
			AutomationHelper.writeToLogsAsIs("\n***Failed to interact with Widget identified by"
					+ "\n\tSelector: "+selector.toString()+"***");
			AutomationHelper.logExceptionAndThrowable(e.getMessage(), e);
		}//catch
		finally{}
	}
		
	
	/**
	 * Usually for debugging purposes, allow a page to highlight the widget it's working with
	 * @param driver reference to the WebDriver crawling the page
	 * @param widget XPath or CSS locator of the widget the driver is working with
	 */
	protected void extraHighlightWidget(WebDriver driver, By widget){
		WebElement element = driver.findElement(widget);
		for (int i=0; i<28; i++){
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
							element, "color:yellow; border:12px solid yellow; border-style: dashed;");
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
								element, "");
		}
	}
	

	/**
	 * Method facilitates clearing a field (field or text area)
	 * @param driver crawling the page
	 * @param selector CSS or XPath to the widget that's being cleared of text
	 */
	protected void clearField(WebDriver driver, By selector){
		try{
			if(isElementPresent(driver, selector)){
				driver.findElement(selector).clear();
			}//if present
		}catch(Exception e){
			AutomationHelper.writeToLogsAsIs("\n***Failed to interact with Widget identified by"
					+ "\n\tSelector: "+selector.toString()+"***");
			AutomationHelper.logExceptionAndThrowable(e.getMessage(), e);
			// 
				//TestCaseConstants.SCREENSHOT_PATH,"clearFieldFailure");
		}//catch
	}
	/**
	 * Method facilitates the sending of text to a widget (clears field first)
	 * @param selector be it XPath or CSS, this selects the widget
	 * @param text to be send to the widget
	 */
	protected void sendText(WebDriver driver, By selector, String text){
		try{
			waitForIt(driver,selector);
			clearField(driver, selector);
			AutomationHelper.writeToLogsAsIs("Send text to Widget: "+selector.toString());
			findElement(driver, selector).sendKeys(text);
		}catch(Exception e){
			AutomationHelper.writeToLogsAsIs("\n***Failed to interact with Widget identified by"
					+ "\n\tSelector: "+selector.toString()+"***");
			AutomationHelper.logExceptionAndThrowable(e.getMessage(), e);	
		}//catch
		finally{}
	}
	
	/**
	 * Scroll browser window to Point(0,0)
	 */
	protected void scrollWindowToTop(){			
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");
	}
	
	/**
	 * Allows the page to take a screenshot. A time stamp is added  
	 * @param driver reference to the WebDriver crawling the page
	 * @param path where to save the screenshot
	 * @param testClassName name of test case class that took the screenshot
	 */
	public static void takeAScreenshot(WebDriver driver, String path,
										String testClassName){
		/*
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		File screenshot = 
			((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
			*/
		File screenshot = 
				((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, 
					new File(path+"screenshot-"+
							testClassName+"-"+
							AutomationHelper.getRandomNumberInt(1000000)+".png"));
		} catch (IOException e) {
			AutomationHelper.logExceptionAndThrowable("\n** Couldn't save file in the "
							+ "desired location **\n"+e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	/**
	 * Wait for element to be present in the DOM
	  * @param driver WebDriver that's crawling the page
	 * @param by the selector to get at the widget
	 *@return reference to the {@link WebElement} widget, , or null if there is no reference to a widget
	 * <p>
	 * 	 Note: I could have used a Null object design pattern or throw an exception but I'm simplifying the
	 * 		   method's usage. Besides, we want the test case to fail immediately if the widget isn't found.
	 * </p>
	 */
	protected WebElement waitForIt(WebDriver driver, By by){
		WebDriverWait wait = new WebDriverWait(driver, WAIT_TIME, POLL_TIME);
		try{
			//WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
		return element;
		}catch(Exception e){
			AutomationHelper.writeToLogsAsIs("\n*** Failed to interact with Widget identified by"
					+ "\n\tSelector: "+by.toString()+"***");
			AutomationHelper.logExceptionAndThrowable(e.getMessage(), e);
		}//catch
		finally{}
		return null;
	}
	
	/**
	 * Sleep in the thread for (time) seconds
	 * @param time how long (in seconds) to wait
	 */
	public static void waitForAwhile(long time){
		boolean interrupted = false;
		try{
			Thread.sleep(time);
		} catch (InterruptedException e) { 
			interrupted = true; // fall through and retry 
			AutomationHelper.logStackTrace("Thread Error: "+e.getMessage());
		} 
		finally{ 
			if (interrupted) Thread.currentThread().interrupt(); 
		}
		
	}
	
	/**
	 * Special case method that asserts whether widgets are present by checking the size of the widget:
	 * if greater than 0 the widget is probably there ... it doesn't have to be enabled, just present 
	 * sufficient for checking if present purposes 
	 * @param driver crawling the page
	 * @param locator CSS,or XPATH locator used to find the widgets
	 * @return true if widgets are on the page
	 */
	protected boolean areElementsSized(WebDriver driver, By locator){
		try{
			List<WebElement> els = findElements(driver, locator);
			for(WebElement el: els) {
				highlightWidget(driver, el);
			}
			return (driver.findElements(locator).size() > 0);
		}catch (Throwable e){
			AutomationHelper.writeToLogsAsIs("\n***Failed to interact with Widget identified by"
					+ "\n\tSelector: "+locator.toString()+"***");
			AutomationHelper.logExceptionAndThrowable(e.getMessage(), e);
			return false;
		}
	}
	
	/*
	* OMMITTED METHODS BELOW, but I hope the point has been made: this class has all the functionality
	* that are common to page objects extending this class
	*/
	
}


