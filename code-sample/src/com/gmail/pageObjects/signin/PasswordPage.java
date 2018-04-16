/**
 * 
 */
package com.gmail.pageObjects.signin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.gmail.pageObjects.BasePage;
import com.gmail.pageObjects.GoogleLandingPage;
import com.gmail.pageObjects.mailbox.GmailMailbox;

/**
 * This represents the page object for the password page
 * @author IRiley
 *
 */
public class PasswordPage extends BasePage {
	
	//username div
	private By identifier = By.cssSelector("div#profileIdentifier");
	private By passwordField = By.cssSelector("input[name='password']");
	private By nextButton = By.cssSelector("div#passwordNext");

	public PasswordPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.gmail.pageObjects.BasePage#openPage(org.openqa.selenium.WebDriver)
	 */
	@Override
	public void openPage(WebDriver driver) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Asserts that the username is displayed in the div
	 * @param driver traversing the page
	 * @return true if the username is displayed in the div
	 */
	public boolean isUsernname(WebDriver driver) {
		waitForIt(driver, identifier);
		return  isElementSized(driver, identifier);
	}
	
	/**
	 * Enters the password
	 * @param driver traversing the page
	 * @param password the password
	 */
	public void enterPassword(WebDriver driver, String password) {
		sendText(driver, passwordField, password);
	}

	/**
	 * Redirects the user to the landing page (the Google search page) if the user clicks the Sign In button
	 * before clicking the Gmail button 
	 * @param driver traversing the page
	 * @return reference to the {@link GoogleLandingPage}
	 */
	public GoogleLandingPage nextWhenThruSigninFirst(WebDriver driver) {
		waitForIt(driver, nextButton);
		return new GoogleLandingPage(driver);
	}
	
	/**
	 * Redirects the user to the mailbox when she clicks the Gmail button first (coming from the landing page)
	 * @param driver traversing the page
	 * @return reference to the {@link GmailMailbox}
	 */
	public GmailMailbox nextWhenGmailClickedFirst(WebDriver driver) {
		waitForIt(driver, nextButton);
		clickElement(driver, nextButton);
		waitForAwhile(3000);
		return new GmailMailbox(driver);
	}
}
