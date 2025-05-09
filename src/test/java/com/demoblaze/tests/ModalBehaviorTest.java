package com.demoblaze.tests;

import com.demoblaze.pages.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;

public class ModalBehaviorTest extends BaseTest {
    
    private AboutUsPage aboutUsPage;

    @BeforeMethod
    public void setupModalsTest() {
        aboutUsPage = new AboutUsPage(driver);
    }

    @Test(description = "DEFECT: ESC key does not close Login modal as expected by UI/UX standards")
    public void testLoginModalDoesNotCloseWithEsc() {
        homePage.clickLoginLink();
        
        assertTrue(loginPage.isLoginModalDisplayed(), "Login modal should be displayed");
        
        loginPage.pressEscapeKey();
        
        assertTrue(loginPage.isLoginModalDisplayed(), "DEFECT: Login modal should close with ESC key by UI/UX standards but remains open");
    }
    
    @Test(description = "DEFECT: ESC key does not close Sign Up modal as expected by UI/UX standards")
    public void testSignUpModalDoesNotCloseWithEsc() {
        homePage.clickSignUpLink();
        
        assertTrue(signUpPage.isSignUpModalDisplayed(), "Sign Up modal should be displayed");
        
        signUpPage.pressEscapeKey();
        
        assertTrue(signUpPage.isSignUpModalDisplayed(), "DEFECT: Sign Up modal should close with ESC key by UI/UX standards but remains open");
    }
    
    @Test(description = "DEFECT: ESC key does not close Contact modal as expected by UI/UX standards")
    public void testContactModalDoesNotCloseWithEsc() {
        homePage.clickContactLink();
        
        assertTrue(contactPage.isContactModalDisplayed(), "Contact modal should be displayed");
        
        contactPage.pressEscapeKey();
        
        assertTrue(contactPage.isContactModalDisplayed(), "DEFECT: Contact modal should close with ESC key by UI/UX standards but remains open");
    }
    
    @Test(description = "DEFECT: ESC key does not close About Us modal as expected by UI/UX standards")
    public void testAboutUsModalDoesNotCloseWithEsc() {
        aboutUsPage.clickAboutUsLink();
        
        assertTrue(aboutUsPage.isAboutUsModalDisplayed(), "About Us modal should be displayed");
        
        aboutUsPage.pressEscapeKey();
        
        assertFalse(aboutUsPage.isAboutUsModalDisplayed(), "DEFECT: About Us modal should close with ESC key by UI/UX standards but remains open");
        
        aboutUsPage.clickCloseButton();
        
        
        if (aboutUsPage.isAboutUsModalDisplayed()) {
            System.out.println("ADDITIONAL DEFECT: The Close button also fails to close the About Us modal");
        }
    }
    
    @Test(description = "DEFECT: All modals fail to close with ESC key, demonstrating site-wide UI/UX issue")
    public void testAllModalsESCBehavior() {
        homePage.clickLoginLink();
        loginPage.pressEscapeKey();
        boolean loginModalStaysOpen = loginPage.isLoginModalDisplayed();
        loginPage.clickCloseButton();
        
        homePage.clickSignUpLink();
        signUpPage.pressEscapeKey();
        boolean signUpModalStaysOpen = signUpPage.isSignUpModalDisplayed();
        signUpPage.clickCloseButton();
        
        homePage.clickContactLink();
        contactPage.pressEscapeKey();
        boolean contactModalStaysOpen = contactPage.isContactModalDisplayed();
        contactPage.clickCloseButton();
        
        aboutUsPage.clickAboutUsLink();
        aboutUsPage.pressEscapeKey();
        boolean aboutUsModalStaysOpen = aboutUsPage.isAboutUsModalDisplayed();
        aboutUsPage.clickCloseButton();
        
        assertTrue(loginModalStaysOpen && signUpModalStaysOpen && contactModalStaysOpen && aboutUsModalStaysOpen,
                "DEFECT: None of the modals close with ESC key, which violates standard UI/UX practices");
    }
}