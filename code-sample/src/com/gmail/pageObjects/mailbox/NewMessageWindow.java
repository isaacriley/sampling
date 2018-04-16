/**
 * 
 */
package com.gmail.pageObjects.mailbox;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.gmail.pageObjects.BasePage;

/**
 * This is the page object for the New Message window
 * @author IRiley
 *
 */
public class NewMessageWindow extends BasePage {
	
	private By toField = By.xpath("//textarea[@name='to']");
	private By subjectField = By.xpath("//input[@name='subjectbox']");
	private By messageBody = By.xpath("//div[contains(@aria-label,'Message')]");
	private By sendButton = By.xpath("//div[.='Send' and contains(@data-tooltip, 'Send')]");
	private By discardDraftBtn = By.xpath("//div[@aria-label='Discard draft']");
	private By saveAndCloseButton = By.xpath("//img[contains(@aria-label,'Save')]");
;
	public NewMessageWindow(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.gmail.pageObjects.BasePage#openPage(org.openqa.selenium.WebDriver)
	 */
	@Override
	public void openPage(WebDriver driver) {}
	
	/**
	 * Sets the "Recipient" email field
	 * @param recipients to recipient(s)
	 */
	public void setRecipients(String... recipients) {
		waitForIt(driver, toField);
		clearField(driver, toField);
		for(int i =0; i < recipients.length; i++) {
			driver.findElement(toField).sendKeys(recipients[i]);
			if(recipients.length >1)
				driver.findElement(toField).sendKeys(",");
		}//for 
	}

	/**
	 * Enters the subject of the email
	 * @param driver traverses the page
	 * @param subject the subject of the email
	 */
	public void enterSubject(WebDriver driver, String subject) {
		sendText(driver, subjectField,subject);
	}
	
	/**
	 * Enters the text that goes in the message body of the email
	 * @param driver traverses the page
	 * @param message the message of the email
	 */
	public void enterMessage(WebDriver driver, String message) {
		waitForIt(driver, messageBody);
		driver.findElement(messageBody).sendKeys(message);
	}
	
	/**
	 * Sends the email
	 * @param driver traverses the page
	 */
	public void send(WebDriver driver) {
		waitForIt(driver, sendButton);
		clickElement(driver, sendButton);
	}
	
	/**
	 * Discards the draft
	 * @param driver traverses the page
	 */
	public void discardDraft(WebDriver driver){
	
		waitForIt(driver, discardDraftBtn);
		clickElement(driver, discardDraftBtn);
		
	}
	
	/**
	 * Closes and save the draft 
	 * @param driver traverses the page
	 * @return reference to {@link GmailMailbox}
	 */
	public GmailMailbox closeWindow(WebDriver driver) {
		waitForIt(driver, saveAndCloseButton);
		clickElement(driver, saveAndCloseButton);
		return new GmailMailbox(driver);
	}
	
	
}
