/**
 * 
 */
package com.gmail.pageObjects.mailbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.gmail.pageObjects.BasePage;

/**
 * The page object of the small dropdown hangs below the account button when it is clicked
 *@author IRiley
 *
 */
public class AccountDropdown extends BasePage {

	private By signoutButton = By.xpath("//a[.='Sign out']"); 
	public AccountDropdown(WebDriver driver) {
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

	public void signout(WebDriver driver) {
		waitForIt(driver, signoutButton);
		clickElement(driver, signoutButton);
	}
}
