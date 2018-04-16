/**
 * 
 */
package com.gmail.pageObjects.signin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.gmail.pageObjects.BasePage;

/**
 * This is the page object of the sign-in page for gmail accounts
 * @author IRiley
 *
 */
public class UsernamePage extends BasePage {
	
	private By usernameField = By.cssSelector("input#identifierId");
	private By forgotEmailButton = By.xpath("//button[contains(.,'Forgot email?')]");
	private By missingEmailErrorMessage = By.xpath("//div[.='Enter an email or phone number']");
	private By nextButton = By.cssSelector("div#identifierNext");

	public UsernamePage(WebDriver driver) {
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
	 * Enters the username/id/phone number
	 * @param driver crawling the page
	 * @param username the username
	 */
	public void enterUsername(WebDriver driver, String username) {
		sendText(driver, usernameField, username);
	}
	
	public void clickForgotEmailButton(WebDriver driver) {
		//@TODO flush it out later
		waitForIt(driver, forgotEmailButton);
	}
	
	/**
	 * Asserts that the email/phone number missing message is rendered
	 * @param driver traversing the page
	 * @return true if the email/phone number missing message is rendered
	 */
	public boolean isEmptyEmailErrorMessageRenderd(WebDriver driver) {
		waitForIt(driver, missingEmailErrorMessage);
		return isElementSized(driver, missingEmailErrorMessage);
	}
	
	/**
	 * Transitions the user to the page that enters the password
	 * @param driver traverses the page
	 * @return reference to the {@link PasswordPage}
	 */
	public PasswordPage clickNextButton(WebDriver driver) {
		waitForIt(driver, nextButton);
		clickElement(driver, nextButton);
		return new PasswordPage(driver);
	}
	
}
