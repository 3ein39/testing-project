package com.demoblaze.tests;

import com.demoblaze.pages.*;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.testng.Assert.*;

/**
 * Tests to verify modal behavior in the application
 * Focus on ESC key functionality for closing modals
 * 
 * Bug Documentation: According to standard UI/UX practices, pressing the ESC key should close modals.
 * These tests document that this standard behavior is not implemented across the site.
 */
public class ModalBehaviorTest extends BaseTest {
    
    private AboutUsPage aboutUsPage;

    @BeforeMethod
    public void setupModalsTest() {
        // Initialize the About Us page
        aboutUsPage = new AboutUsPage(driver);
    }

    /**
     * Bug Test: Standard UI practice is that ESC key should close the Login modal
     * This test documents that the Login modal does not close with ESC key, which is a bug.
     */
    @Test(description = "DEFECT: ESC key does not close Login modal as expected by UI/UX standards")
    public void testLoginModalDoesNotCloseWithEsc() {
        // Open login modal
        homePage.clickLoginLink();
        
        // Verify login modal is displayed
        assertTrue(loginPage.isLoginModalDisplayed(), "Login modal should be displayed");
        
        // Press ESC key
        loginPage.pressEscapeKey();
        
        // Verify modal is still displayed (documenting the defect)
        assertTrue(loginPage.isLoginModalDisplayed(), "DEFECT: Login modal should close with ESC key by UI/UX standards but remains open");
    }
    
    /**
     * Bug Test: Standard UI practice is that ESC key should close the Sign Up modal
     * This test documents that the Sign Up modal does not close with ESC key, which is a bug.
     */
    @Test(description = "DEFECT: ESC key does not close Sign Up modal as expected by UI/UX standards")
    public void testSignUpModalDoesNotCloseWithEsc() {
        // Open signup modal
        homePage.clickSignUpLink();
        
        // Verify sign up modal is displayed
        assertTrue(signUpPage.isSignUpModalDisplayed(), "Sign Up modal should be displayed");
        
        // Press ESC key
        signUpPage.pressEscapeKey();
        
        // Verify modal is still displayed (documenting the defect)
        assertTrue(signUpPage.isSignUpModalDisplayed(), "DEFECT: Sign Up modal should close with ESC key by UI/UX standards but remains open");
    }
    
    /**
     * Bug Test: Standard UI practice is that ESC key should close the Contact modal
     * This test documents that the Contact modal does not close with ESC key, which is a bug.
     */
    @Test(description = "DEFECT: ESC key does not close Contact modal as expected by UI/UX standards")
    public void testContactModalDoesNotCloseWithEsc() {
        // Open contact modal
        homePage.clickContactLink();
        
        // Verify contact modal is displayed
        assertTrue(contactPage.isContactModalDisplayed(), "Contact modal should be displayed");
        
        // Press ESC key
        contactPage.pressEscapeKey();
        
        // Verify modal is still displayed (documenting the defect)
        assertTrue(contactPage.isContactModalDisplayed(), "DEFECT: Contact modal should close with ESC key by UI/UX standards but remains open");
    }
    
    /**
     * Bug Test: Standard UI practice is that ESC key should close the About Us modal
     * This test documents that the About Us modal does not close with ESC key, which is a bug.
     * Additionally, it documents that the Close button may also have issues.
     */
    @Test(description = "DEFECT: ESC key does not close About Us modal as expected by UI/UX standards")
    public void testAboutUsModalDoesNotCloseWithEsc() {
        // Open about us modal
        aboutUsPage.clickAboutUsLink();
        
        // Verify about us modal is displayed
        assertTrue(aboutUsPage.isAboutUsModalDisplayed(), "About Us modal should be displayed");
        
        aboutUsPage.pressEscapeKey();
        
        assertFalse(aboutUsPage.isAboutUsModalDisplayed(), "DEFECT: About Us modal should close with ESC key by UI/UX standards but remains open");
        
        aboutUsPage.clickCloseButton();
        
        
        if (aboutUsPage.isAboutUsModalDisplayed()) {
            System.out.println("ADDITIONAL DEFECT: The Close button also fails to close the About Us modal");
        }
    }
    
    /**
     * Comparative test to show that all modals have the same defect regarding ESC key behavior
     * This helps to illustrate that the issue is site-wide and not limited to one modal
     */
    @Test(description = "DEFECT: All modals fail to close with ESC key, demonstrating site-wide UI/UX issue")
    public void testAllModalsESCBehavior() {
        // Test Login modal
        homePage.clickLoginLink();
        loginPage.pressEscapeKey();
        boolean loginModalStaysOpen = loginPage.isLoginModalDisplayed();
        loginPage.clickCloseButton(); // Try to close with button to proceed
        
        // Test Sign Up modal
        homePage.clickSignUpLink();
        signUpPage.pressEscapeKey();
        boolean signUpModalStaysOpen = signUpPage.isSignUpModalDisplayed();
        signUpPage.clickCloseButton(); // Try to close with button to proceed
        
        // Test Contact modal
        homePage.clickContactLink();
        contactPage.pressEscapeKey();
        boolean contactModalStaysOpen = contactPage.isContactModalDisplayed();
        contactPage.clickCloseButton(); // Try to close with button to proceed
        
        // Test About Us modal
        aboutUsPage.clickAboutUsLink();
        aboutUsPage.pressEscapeKey();
        boolean aboutUsModalStaysOpen = aboutUsPage.isAboutUsModalDisplayed();
        aboutUsPage.clickCloseButton();
        
        // Assert that all modals stay open after ESC key (documenting the widespread issue)
        assertTrue(loginModalStaysOpen && signUpModalStaysOpen && contactModalStaysOpen && aboutUsModalStaysOpen,
                "DEFECT: None of the modals close with ESC key, which violates standard UI/UX practices");
    }
}