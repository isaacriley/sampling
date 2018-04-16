/**
 * 
 */
package com.gmail.pageObjects.mailbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.gmail.pageObjects.BasePage;

/**
 * This is the page object for the Gmail Mailbox
 * @author IRiley
 *
 */
public class GmailMailbox extends BasePage {
	
	private By composeButton = By.xpath("//div[.='COMPOSE' and @role='button']");
	private By deleteButton = By.xpath("//div[@data-tooltip='Delete' and @role='button']");
	private By accountButton = By.xpath("//a[contains(@title,'Account')]");
	public GmailMailbox(WebDriver driver) {
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
	 * Deletes selected mail
	 * @param driver traversing the page
	 */
	public void clickDeleteButton(WebDriver driver) {
		waitForIt(driver, deleteButton);
		clickElement(driver, deleteButton);
	}
	
	/**
	 * Spawns the window to compose a new message
	 * @param driver traverses the page
	 * @return reference to the {@link NewMessageWindow}
	 */
	public NewMessageWindow clickComposeButton(WebDriver driver) {
		
		waitForIt(driver, composeButton);
		clickElement(driver, composeButton);
		return new NewMessageWindow(driver);
	}
	
	/**
	 * Clicks on the Account avatar
	 * @param driver traverses the page
	 * @return reference to {@link AccountDropdown}
	 */
	public AccountDropdown clickAccountButton(WebDriver driver) {
		waitForIt(driver, accountButton);
		clickElement(driver, accountButton);
		return new AccountDropdown(driver);
	}
}
