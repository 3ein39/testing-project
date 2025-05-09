package com.demoblaze.tests;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.Reporter;
import static org.testng.Assert.*;

import java.util.List;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DemoBlazeTest extends BaseTest {

    @Test
    public void testHomePageNavigation() {
        assertTrue(homePage.getPageTitle().contains("STORE"), "Page title should contain 'STORE'");
        
        assertTrue(homePage.isLoginLinkDisplayed(), "Login link should be displayed");
        assertTrue(homePage.isSignUpLinkDisplayed(), "Sign up link should be displayed");
        assertTrue(homePage.isCartLinkDisplayed(), "Cart link should be displayed");
        assertTrue(homePage.isContactLinkDisplayed(), "Contact link should be displayed");
        
        assertTrue(homePage.isPhonesCategoryDisplayed(), "Phones category should be displayed");
        assertTrue(homePage.isLaptopsCategoryDisplayed(), "Laptops category should be displayed");
        assertTrue(homePage.isMonitorsCategoryDisplayed(), "Monitors category should be displayed");
        
        assertTrue(homePage.isProductListDisplayed(), "Product list should be displayed");
    }

    @Test
    public void testLoginFunctionality() {
        login("reveari", "reveari");
        
        waitHelper.waitForElementVisible(loginPage.getWelcomeMessageElement());
        assertTrue(loginPage.isWelcomeMessageDisplayed(), "Welcome message should be displayed after login");
        String welcomeMessage = loginPage.getWelcomeMessage();
        assertTrue(welcomeMessage.contains("Welcome"), "Welcome message should contain 'Welcome'");
    }
    
  
    @Test
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
    
    @Test
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
    
    @Test
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
    
    @Test
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
    
    @Test
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

    @Test
    public void testProductSelection() {
        assertTrue(productPage.isProductListDisplayed(), "Product list should be displayed");
        
        String productName = "Samsung galaxy s6";
        addProductToCart(productName);
        
        productPage.goToCart();
        
        waitHelper.waitForPageLoad();
        
        assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one item");
    }

    @Test
    public void testCartFunctionality() {
        addProductToCart("Samsung galaxy s6");
        
        productPage.goToCart();
        
        waitHelper.waitForPageLoad();
        
        assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one item");
    }

    @Test
    public void testCompletePurchase() {
        Reporter.log("Starting testCompletePurchase...", true);
        
        addProductToCart("Samsung galaxy s6");
        
        productPage.goToCart();
        cartPage.clickPlaceOrder();
        
        cartPage.fillPurchaseForm(
            "Test User",
            "Test Country",
            "Test City",
            "4111111111111111",
            "12",
            "2025"
        );
        
        cartPage.clickPurchase();
        
        String confirmation = cartPage.getOrderConfirmation();
        Reporter.log("Confirmation text: " + confirmation, true);
        
        assertTrue(confirmation.contains("Thank you for your purchase"), 
            "Order confirmation message should be displayed. Actual message: " + confirmation);
    }

    @Test
    public void testPhonesCategory() {
        categoryPage.clickPhonesCategory();
        assertTrue(categoryPage.isCategoryPageLoaded("phone"), "Phones category page should be loaded");
        assertTrue(categoryPage.getProductCount() > 0, "Phones category should have products");
        List<String> phoneProducts = categoryPage.getProductNames();
        assertTrue(phoneProducts.stream().anyMatch(name -> name.contains("Samsung")), 
            "Phones category should contain Samsung products");
    }

    @Test
    public void testLaptopsCategory() {

        categoryPage.clickLaptopsCategory();
        assertTrue(categoryPage.isCategoryPageLoaded("laptop"), "Laptops category page should be loaded");
        assertTrue(categoryPage.getProductCount() > 0, "Laptops category should have products");
        List<String> laptopProducts = categoryPage.getProductNames();
        assertTrue(laptopProducts.stream().anyMatch(name -> name.contains("MacBook")), 
            "Laptops category should contain MacBook products");
    }

    @Test
    public void testCategoryNavigation() {
        categoryPage.clickPhonesCategory();
        assertTrue(categoryPage.isCategoryPageLoaded("phone"), "Should be on phones category");
        
        categoryPage.clickLaptopsCategory();
        assertTrue(categoryPage.isCategoryPageLoaded("laptop"), "Should be on laptops category");
        
        categoryPage.clickMonitorsCategory();
        assertTrue(categoryPage.isCategoryPageLoaded("monitor"), "Should be on monitors category");
    }

    @Test
    public void testProductFromCategory() {
        categoryPage.clickPhonesCategory();
        waitHelper.waitForPageLoad();
        
        addProductToCart("Samsung galaxy s6");
        
        productPage.goToCart();
        
        waitHelper.waitForPageLoad();
  
        assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one item");
    }

    @Test
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
    
    
    @Test
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
    
    @Test
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
    
    @Test
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
    
    @Test
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
    
    @Test
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

    @Test
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
    
    @DataProvider(name = "categoryTestData")
    public Object[][] getCategoryData() {
        return new Object[][] {
            {"phone", "Samsung"},
            {"laptop", "MacBook"},
            {"monitor", "Apple"}
        };
    }

    @Test(dataProvider = "categoryTestData")
    public void testProductCategory(String category, String expectedBrand) {
        Reporter.log("Testing " + category + " category, expecting " + expectedBrand + " products", true);

        switch (category.toLowerCase()) {
            case "phone":
                categoryPage.clickPhonesCategory();
                break;
            case "laptop":
                categoryPage.clickLaptopsCategory();
                break;
            case "monitor":
                categoryPage.clickMonitorsCategory();
                break;
        }

        assertTrue(categoryPage.isCategoryPageLoaded(category),
                category + " category page should be loaded");

        assertTrue(categoryPage.getProductCount() > 0,
                category + " category should have products");

        List<String> products = categoryPage.getProductNames();
        Reporter.log("Found products: " + products, true);

        assertTrue(products.stream().anyMatch(name -> name.contains(expectedBrand)),
                category + " category should contain " + expectedBrand + " products");
    }

    @Test
    public void testInvalidPurchaseFormData() {
        Reporter.log("Starting testInvalidPurchaseFormData...", true);
        
        addProductToCart("Samsung galaxy s6");
        
        productPage.goToCart();
        cartPage.clickPlaceOrder();
        
        cartPage.fillPurchaseForm(
            "Test User",
            "Test Country",
            "Test City",
            "Invalid-Card-Number",
            "ABC",
            "XYZ"
        );
        
        cartPage.clickPurchase();
        
        String confirmation = cartPage.getOrderConfirmation();
        Reporter.log("Confirmation text: " + confirmation, true);
        
        assertFalse(confirmation.contains("Thank you for your purchase"),
            "Order confirmation should appear despite invalid numeric data, highlighting the validation issue");
    }
    
    @Test
    public void testEmptyCartPurchase() {
        Reporter.log("Starting testEmptyCartPurchase...", true);
        
        homePage.clickCartLink();
        
        assertTrue(cartPage.isCartEmpty(), "Cart should be empty for this test");
        
        cartPage.clickPlaceOrder();
        
        cartPage.fillPurchaseForm(
            "Test User",
            "Test Country",
            "Test City",
            "4111111111111111",
            "12",
            "2025"
        );
        
        cartPage.clickPurchase();
        
        String confirmation = cartPage.getOrderConfirmation();
        Reporter.log("Confirmation text: " + confirmation, true);
        
        assertFalse(confirmation.contains("Thank you for your purchase"),
            "Order confirmation should not appear with empty cart, highlighting the validation issue");
    }

    @Test
    public void testPartialPurchaseFormValidation() {
        Reporter.log("Starting testPartialPurchaseFormValidation...", true);
        
        addProductToCart("Samsung galaxy s6");
        
        productPage.goToCart();
        cartPage.clickPlaceOrder();
        
        waitHelper.waitForElementVisible(cartPage.getNameField()).sendKeys("Test User");
        waitHelper.waitForElementVisible(cartPage.getCardField()).sendKeys("4111111111111111");
        
        cartPage.clickPurchase();
        
        String confirmation = cartPage.getOrderConfirmation();
        Reporter.log("Confirmation text: " + confirmation, true);
        
        assertFalse(confirmation.contains("Thank you for your purchase"),
            "Order confirmation should not appear with incomplete form data, highlighting the validation issue");
    }

    @Test
    public void testPurchaseDateIsCorrect() {
        Reporter.log("Starting testPurchaseIsCorrect...", true);
        
        addProductToCart("Samsung galaxy s6");
        
        productPage.goToCart();
        cartPage.clickPlaceOrder();
        
        cartPage.fillPurchaseForm(
            "Test User",
            "Test Country",
            "Test City",
            "4111111111111111",
            "12",
            "2025"
        );
        
        cartPage.clickPurchase();
        
        String confirmationText = cartPage.getCompleteOrderConfirmation();
        Reporter.log("Full confirmation text: " + confirmationText, true);
        
        Calendar currentDate = Calendar.getInstance();
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentYear = currentDate.get(Calendar.YEAR);
        
        Reporter.log("Current date: " + currentDay + "/" + currentMonth + "/" + currentYear, true);
        
        Pattern datePattern = Pattern.compile("Date:\\s*(\\d+)/(\\d+)/(\\d+)");
        Matcher matcher = datePattern.matcher(confirmationText);
        
        if (matcher.find()) {
            int displayedMonth = Integer.parseInt(matcher.group(2));
            int displayedDay = Integer.parseInt(matcher.group(1));
            int displayedYear = Integer.parseInt(matcher.group(3));
            
            Reporter.log("Displayed date: " + displayedDay + "/" + displayedMonth + "/" + displayedYear, true);
            
            assertEquals(displayedMonth, currentMonth, "Displayed month should be equal to current month");

            assertEquals(displayedDay, currentDay, "Displayed day should be equal to current day");

            assertEquals(displayedYear, currentYear, "Displayed year should be equal to current year");

        } else {
            fail("Could not find date in confirmation text: " + confirmationText);
        }
    }

    @Test
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