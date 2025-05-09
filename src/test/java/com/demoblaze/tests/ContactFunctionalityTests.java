package com.demoblaze.tests;

import org.testng.Reporter;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


public class ContactFunctionalityTests extends BaseTest {

    @Test(description = "Verify contact form submission")
    public void testContactFunctionality() {
        homePage.clickContactLink();
        
        String email = "test@example.com";
        String name = "Test User";
        String message = "This is a test message";
        
        contactPage.sendContactMessage(email, name, message);
        
        String alertText = alertHelper.handleAlert("Thanks for the message");
        assertTrue(alertText.contains("Thanks for the message"), 
                "Contact message should be sent successfully");
    }
    

    @Test(description = "Verify contact form validation with invalid email")
    public void testInvalidEmailContactForm() {
        Reporter.log("Starting testInvalidEmailContactForm...", true);
        
        homePage.clickContactLink();
        
        String invalidEmail = "invalid-email";
        String name = "Test User";
        String message = "This is a test message";
        
        contactPage.sendContactMessage(invalidEmail, name, message);
        
        String alertText = alertHelper.handleAlert("Thanks for the message");
        
        assertFalse(alertText.contains("Thanks for the message"),
                "Contact form should not allow invalid email submission, but it does, highlighting the validation issue");
    }

    @Test(description = "Verify empty message field submission")
    public void testEmptyMessageField() {
        Reporter.log("Starting testEmptyMessageField...", true);
        
        homePage.clickContactLink();
        
        String email = "hh1681@fayoum.edu.eg";
        String name = "Test User";
        String message = "";

        contactPage.sendContactMessage(email, name, message);

        String alertText = alertHelper.handleAlert("Thanks for the message");
        assertFalse(alertText.contains("Thanks for the message"),
                "Contact form should not allow empty message submission, but it does, highlighting the validation issue");
    }

    @Test(description = "Verify empty contact form submission validation")
    public void testEmptyContactFormSubmission() {
        Reporter.log("Starting testEmptyContactFormSubmission...", true);

        homePage.clickContactLink();

        assertTrue(contactPage.isContactModalDisplayed(), "Contact modal should be displayed");

        contactPage.clickSendMessageButton();

        String alertText = alertHelper.handleAlert("Thanks for the message");

        Reporter.log("Alert text: " + alertText, true);

        assertFalse(alertText.contains("Thanks for the message"),
                "Contact form should not allow empty submission, but it does, highlighting the validation issue");
    }
}

