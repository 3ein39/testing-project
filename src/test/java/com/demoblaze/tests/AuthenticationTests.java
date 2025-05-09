package com.demoblaze.tests;

import org.testng.Reporter;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class AuthenticationTests extends BaseTest {

    @Test(description = "Verify basic login functionality")
    public void testLoginFunctionality() {
        login("reveari", "reveari");
        
        waitHelper.waitForElementVisible(loginPage.getWelcomeMessageElement());
        assertTrue(loginPage.isWelcomeMessageDisplayed(), "Welcome message should be displayed after login");
        String welcomeMessage = loginPage.getWelcomeMessage();
        assertTrue(welcomeMessage.contains("Welcome"), "Welcome message should contain 'Welcome'");
    }
    
    @Test(description = "Verify login behavior with incorrect password")
    public void testLoginWithWrongPassword() {
        Reporter.log("Starting testLoginWithWrongPassword...", true);
        
        homePage.clickLoginLink();
        
        loginPage.enterUsername("reveari");
        loginPage.enterPassword("wrongpassword");
        loginPage.clickLoginButton();
        
        String alertText = alertHelper.handleAlert("Wrong password");
        assertTrue(alertText.contains("Wrong password"), 
                "Alert should indicate wrong password. Actual: " + alertText);
                
        assertFalse(loginPage.isWelcomeMessageDisplayed(), 
                "Welcome message should not be displayed after failed login");
    }
    
    @Test(description = "Verify login behavior with non-existent user")
    public void testLoginWithNonExistentUser() {
        Reporter.log("Starting testLoginWithNonExistentUser...", true);
        
        String nonExistentUser = "nonexistent" + System.currentTimeMillis();
        
        homePage.clickLoginLink();
        
        loginPage.enterUsername(nonExistentUser);
        loginPage.enterPassword("anypassword");
        loginPage.clickLoginButton();
        
        String alertText = alertHelper.handleAlert("User does not exist");
        assertTrue(alertText.contains("User does not exist"), 
                "Alert should indicate user does not exist. Actual: " + alertText);
                
        assertFalse(loginPage.isWelcomeMessageDisplayed(), 
                "Welcome message should not be displayed after failed login");
    }
    
    @Test(description = "Verify login form validation with empty credentials")
    public void testLoginWithEmptyCredentials() {
        Reporter.log("Starting testLoginWithEmptyCredentials...", true);
        
        homePage.clickLoginLink();
        
        loginPage.clickLoginButton();
        
        String alertText = alertHelper.handleAlert("Please fill");
        assertTrue(alertText.contains("Please fill") || alertText.contains("empty"), 
                "Alert should indicate empty fields. Actual: " + alertText);
                
        assertTrue(loginPage.isLoginModalDisplayed(), 
                "Login modal should still be displayed after failed login");
    }
    
    @Test(description = "Verify login form validation with empty username")
    public void testLoginWithEmptyUsername() {
        Reporter.log("Starting testLoginWithEmptyUsername...", true);
        
        homePage.clickLoginLink();
        
        loginPage.enterPassword("somepassword");
        loginPage.clickLoginButton();
        
        String alertText = alertHelper.handleAlert("Please fill");
        assertTrue(alertText.contains("Please fill") || alertText.contains("empty"), 
                "Alert should indicate empty fields. Actual: " + alertText);
                
        assertTrue(loginPage.isLoginModalDisplayed(), 
                "Login modal should still be displayed after failed login");
    }
    
    @Test(description = "Verify login form validation with empty password")
    public void testLoginWithEmptyPassword() {
        Reporter.log("Starting testLoginWithEmptyPassword...", true);
        
        homePage.clickLoginLink();
        
        loginPage.enterUsername("someusername");
        loginPage.clickLoginButton();
        
        String alertText = alertHelper.handleAlert("Please fill");
        assertTrue(alertText.contains("Please fill") || alertText.contains("empty"), 
                "Alert should indicate empty fields. Actual: " + alertText);
                
        assertTrue(loginPage.isLoginModalDisplayed(), 
                "Login modal should still be displayed after failed login");
    }
    
    @Test(description = "Verify basic signup functionality")
    public void testSignUpFunctionality() {
        String username = "testuser" + System.currentTimeMillis();
        String password = "testpass";
        
        homePage.clickSignUpLink();
        waitHelper.waitForPageLoad();
        
        signUpPage.enterSignUpUsername(username);
        signUpPage.enterSignUpPassword(password);
        signUpPage.clickSignUpButton();
        
        String alertText = alertHelper.handleAlert("Sign up successful");
        assertTrue(alertText.contains("Sign up successful"), "Alert should indicate successful signup");
        
        login(username, password);
        assertTrue(loginPage.isWelcomeMessageDisplayed(), "Should be able to login with new credentials");
    }
    
    @Test(description = "Verify signup form validation with empty credentials")
    public void testSignUpWithEmptyCredentials() {
        Reporter.log("Starting testSignUpWithEmptyCredentials...", true);
        
        homePage.clickSignUpLink();
        
        signUpPage.clickSignUpButton();
        
        String alertText = alertHelper.handleAlert("Please fill");
        assertTrue(alertText.contains("Please fill") || alertText.contains("empty"), 
                "Alert should indicate empty fields. Actual: " + alertText);
                
        assertTrue(signUpPage.isSignUpModalDisplayed(), 
                "Sign up modal should still be displayed after failed signup");
    }
    
    @Test(description = "Verify signup form validation with empty username")
    public void testSignUpWithEmptyUsername() {
        Reporter.log("Starting testSignUpWithEmptyUsername...", true);
        
        homePage.clickSignUpLink();
        
        signUpPage.enterSignUpPassword("somepassword");
        signUpPage.clickSignUpButton();
        
        String alertText = alertHelper.handleAlert("Please fill");
        assertTrue(alertText.contains("Please fill") || alertText.contains("empty"), 
                "Alert should indicate empty fields. Actual: " + alertText);
                
        assertTrue(signUpPage.isSignUpModalDisplayed(), 
                "Sign up modal should still be displayed after failed signup");
    }
    
    @Test(description = "Verify signup form validation with empty password")
    public void testSignUpWithEmptyPassword() {
        Reporter.log("Starting testSignUpWithEmptyPassword...", true);
        
        homePage.clickSignUpLink();
        
        signUpPage.enterSignUpUsername("someusername");
        signUpPage.clickSignUpButton();
        
        String alertText = alertHelper.handleAlert("Please fill");
        assertTrue(alertText.contains("Please fill") || alertText.contains("empty"), 
                "Alert should indicate empty fields. Actual: " + alertText);
                
        assertTrue(signUpPage.isSignUpModalDisplayed(), 
                "Sign up modal should still be displayed after failed signup");
    }
    
    @Test(description = "Verify signup with special character username")
    public void testSignUpWithSpecialCharacters() {
        Reporter.log("Starting testSignUpWithSpecialCharacters...", true);
        
        homePage.clickSignUpLink();
        
        signUpPage.enterSignUpUsername("user@#$%^&*()");
        signUpPage.enterSignUpPassword("password");
        signUpPage.clickSignUpButton();
        
        if (alertHelper.isAlertPresent()) {
            String alertText = alertHelper.handleAlert("");
            if (alertText.contains("Sign up successful")) {
                Reporter.log("Special characters in username were accepted", true);
                
                login("user@#$%^&*()", "password");
                assertTrue(loginPage.isWelcomeMessageDisplayed(), 
                        "Should be able to log in with special character username");
            } else {
                Reporter.log("Special characters in username were rejected: " + alertText, true);
                assertTrue(alertText.contains("invalid") || alertText.contains("special"), 
                        "Alert should indicate invalid characters. Actual: " + alertText);
            }
        }
    }
    
    @Test(description = "Verify signup with very long credentials")
    public void testSignUpWithVeryLongCredentials() {
        Reporter.log("Starting testSignUpWithVeryLongCredentials...", true);
        
        StringBuilder longUsername = new StringBuilder("user");
        StringBuilder longPassword = new StringBuilder("pass");
        
        for (int i = 0; i < 100; i++) {
            longUsername.append("a");
            longPassword.append("b");
        }
        
        homePage.clickSignUpLink();
        
        signUpPage.enterSignUpUsername(longUsername.toString());
        signUpPage.enterSignUpPassword(longPassword.toString());
        signUpPage.clickSignUpButton();
        
        if (alertHelper.isAlertPresent()) {
            String alertText = alertHelper.handleAlert("");
            if (alertText.contains("Sign up successful")) {
                Reporter.log("Very long credentials were accepted", true);
                
                login(longUsername.toString(), longPassword.toString());
                assertTrue(loginPage.isWelcomeMessageDisplayed(), 
                        "Should be able to log in with very long credentials");
            } else {
                Reporter.log("Very long credentials were rejected: " + alertText, true);
                assertTrue(alertText.contains("too long") || alertText.contains("invalid"), 
                        "Alert should indicate credentials too long. Actual: " + alertText);
            }
        }
    }
}