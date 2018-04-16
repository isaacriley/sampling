/**
 * 
 */
package com.gmail.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.gmail.pageObjects.mailbox.GmailMailbox;
import com.gmail.pageObjects.signin.CreateAccountPage;
import com.gmail.pageObjects.signin.UsernamePage;

/**
 * This the page object for the landing page
 * @author IRiley
 *
 */
public class GoogleLandingPage extends BasePage{
	
	private By gmailLink = By.xpath("//a[.='Gmail']");
	private By siginButton = By.xpath("//a[contains(.,'Sign')]");

	public GoogleLandingPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void openPage(WebDriver driver) {	}
	
	/**
	 * Clicks on the Gmail link -- an alternate way of logging into the app
	 * @param driver traverses the page
	 * @return reference to{@link CreateAccountPage}
	 */
	public CreateAccountPage clickGmailButton(WebDriver driver) {
		waitForIt(driver, gmailLink);
		clickElement(driver, gmailLink);
		return new CreateAccountPage(driver);
	}
	
	/**
	 * Redirects user to pages governing the sign in process
	 * @param driver traversing the page
	 * @return reference to {@link UsernamePage}
	 */
	public UsernamePage clickSignInButton(WebDriver driver) {
		waitForIt(driver, siginButton);
		clickElement(driver, siginButton);
		return new UsernamePage(driver);
	}
	
	/**
	 * Redirects the user to the Mailbox when you go through the sign in process first
	 * @param driver traverses the page
	 * @return reference to {@link GmailMailbox}
	 */
	public GmailMailbox clickGmailButtonAfterSigninButton(WebDriver driver) {
		waitForIt(driver, gmailLink);
		clickElement(driver, gmailLink);
		return new GmailMailbox(driver);
	}

	public void gotoHome(WebDriver driver, String url) {
		super.openURL(driver, url);
		driver.manage().window().maximize();
		
	}

}
