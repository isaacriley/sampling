/**
 * 
 */
package com.gmail.test.testCases;

import org.junit.Test;

import com.gmail.constants.TestConstants;
import com.gmail.data.PropertiesFileHandler;
import com.gmail.pageObjects.mailbox.GmailMailbox;
import com.gmail.pageObjects.mailbox.NewMessageWindow;
import com.gmail.test.BaseTestCase;
import com.gmail.util.AutomationHelper;

/**
 * This is the test suite for testing composing and delete emails/drafts
 * @author IRiley
 *
 */
public class CompositionDeletionTest extends  BaseTestCase{
	
	private String username= PropertiesFileHandler.getValue("username", TestConstants.PATH_TO_LOGIN_PROPERTIES_FILE);
	private String password= PropertiesFileHandler.getValue("password", TestConstants.PATH_TO_LOGIN_PROPERTIES_FILE);
	private String to = PropertiesFileHandler.getValue("to", TestConstants.PATH_TO_LOGIN_PROPERTIES_FILE);
	private String subject = PropertiesFileHandler.getValue("subject", TestConstants.PATH_TO_LOGIN_PROPERTIES_FILE);
	private String message = PropertiesFileHandler.getValue("message", TestConstants.PATH_TO_LOGIN_PROPERTIES_FILE);
	private GmailMailbox mailbox;
	private NewMessageWindow compose;
	
	
	public CompositionDeletionTest(String environment) {
		super(environment);
		// TODO Auto-generated constructor stub
	}
	
	private void gotoMailbox() {
		mailbox = loginViaGmailButton(driver, username, password);
	}
	
	@Test
	public void composeDontSendAndDeleteMail() {
		String timestamp = AutomationHelper.getTimeStampWithDashes();
		gotoMailbox();
		compose =mailbox.clickComposeButton(driver);
		compose.setRecipients(to);
		compose.enterSubject(driver, subject+"_"+timestamp+" ["+environment+"]");
		compose.enterMessage(driver, message+timestamp+"\nBrowser: "+environment+"\n Isaac Riley");
		compose.discardDraft(driver);
		logout(driver);
		
	}

}
