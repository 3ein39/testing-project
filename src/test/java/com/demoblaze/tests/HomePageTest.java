package com.demoblaze.tests;

import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;

public class HomePageTest extends BaseTest {

    @Test
    public void testHomePageElements() {
        // Verify multiple homepage elements
        assertTrue(homePage.isLoginLinkDisplayed(), "Login link should be displayed");
        assertTrue(homePage.isSignUpLinkDisplayed(), "Sign up link should be displayed");
        assertTrue(homePage.isCartLinkDisplayed(), "Cart link should be displayed");
        assertTrue(homePage.isContactLinkDisplayed(), "Contact link should be displayed");
    }

    @Test
    public void testNavigationToLogin() {
        // Click login link and verify modal appears
        homePage.clickLoginLink();
        
        // Add proper assertions for login modal
        waitHelper.waitForElementVisible(loginPage.getLoginButton());
        assertTrue(loginPage.isLoginModalDisplayed(), "Login modal should be displayed");
        
        // Verify login form elements
        assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field should be displayed");
        assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field should be displayed");
        assertTrue(loginPage.isLoginButtonDisplayed(), "Login button should be displayed");
    }

    @Test
    public void testNavigationBetweenPages() {
        // Navigate to cart page
        homePage.clickCartLink();
        assertTrue(driver.getCurrentUrl().contains("cart"), "URL should contain 'cart'");

        // Test navigation to contact page
        homePage.clickContactLink();
        assertTrue(contactPage.isContactModalDisplayed(), "Contact modal should be displayed");

        // Navigate back to home
        homePage.clickHomeLink();
        assertTrue(productPage.isProductListDisplayed(), "Product list should be displayed on home page");
    }
}