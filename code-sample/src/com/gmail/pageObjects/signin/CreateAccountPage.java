/**
 * 
 */
package com.gmail.pageObjects.signin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.gmail.pageObjects.BasePage;

/**
 * This is the page object of the springboard page for logging into the mailbox, if coming in via the Gmail link
 * @author IRiley
 */
public class CreateAccountPage extends BasePage{
	
	private By signInButton = By.xpath("//a[.='Sign In']");

	public CreateAccountPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void openPage(WebDriver driver) {
		// TODO Auto-generated method stub	
	}
	
	/**
	 * Redirects user to pages governing the sign in process
	 * @param driver traversing the page
	 * @return reference to the {@link UsernamePage}
	 */
	public UsernamePage clickSignInButton(WebDriver driver) {
		waitForIt(driver, signInButton);
		clickElement(driver, signInButton);
		return new	UsernamePage(driver); 
	}

	
}
