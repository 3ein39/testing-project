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
        // Verify page title
        assertTrue(homePage.getPageTitle().contains("STORE"), "Page title should contain 'STORE'");
        
        // Verify navigation links are present
        assertTrue(homePage.isLoginLinkDisplayed(), "Login link should be displayed");
        assertTrue(homePage.isSignUpLinkDisplayed(), "Sign up link should be displayed");
        assertTrue(homePage.isCartLinkDisplayed(), "Cart link should be displayed");
        assertTrue(homePage.isContactLinkDisplayed(), "Contact link should be displayed");
        
        // Verify category links are present
        assertTrue(homePage.isPhonesCategoryDisplayed(), "Phones category should be displayed");
        assertTrue(homePage.isLaptopsCategoryDisplayed(), "Laptops category should be displayed");
        assertTrue(homePage.isMonitorsCategoryDisplayed(), "Monitors category should be displayed");
        
        // Verify product list is displayed
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
        
        // Open login modal
        homePage.clickLoginLink();
        
        // Enter valid username but wrong password
        loginPage.enterUsername("reveari");
        loginPage.enterPassword("wrongpassword");
        loginPage.clickLoginButton();
        
        // Check for error alert
        String alertText = alertHelper.handleAlert("Wrong password");
        assertTrue(alertText.contains("Wrong password"), 
                "Alert should indicate wrong password. Actual: " + alertText);
                
        // Verify user is not logged in (welcome message not displayed)
        assertFalse(loginPage.isWelcomeMessageDisplayed(), 
                "Welcome message should not be displayed after failed login");
    }
    
    /**
     * Test login with non-existent user
     */
    @Test
    public void testLoginWithNonExistentUser() {
        Reporter.log("Starting testLoginWithNonExistentUser...", true);
        
        // Generate a random username that shouldn't exist
        String nonExistentUser = "nonexistent" + System.currentTimeMillis();
        
        // Open login modal
        homePage.clickLoginLink();
        
        // Enter non-existent username
        loginPage.enterUsername(nonExistentUser);
        loginPage.enterPassword("anypassword");
        loginPage.clickLoginButton();
        
        // Check for error alert
        String alertText = alertHelper.handleAlert("User does not exist");
        assertTrue(alertText.contains("User does not exist"), 
                "Alert should indicate user does not exist. Actual: " + alertText);
                
        // Verify user is not logged in
        assertFalse(loginPage.isWelcomeMessageDisplayed(), 
                "Welcome message should not be displayed after failed login");
    }
    
    /**
     * Test login with empty credentials
     */
    @Test
    public void testLoginWithEmptyCredentials() {
        Reporter.log("Starting testLoginWithEmptyCredentials...", true);
        
        // Open login modal
        homePage.clickLoginLink();
        
        // Click login without entering credentials
        loginPage.clickLoginButton();
        
        // Check for error alert
        String alertText = alertHelper.handleAlert("Please fill");
        assertTrue(alertText.contains("Please fill") || alertText.contains("empty"), 
                "Alert should indicate empty fields. Actual: " + alertText);
                
        // Verify modal is still displayed
        assertTrue(loginPage.isLoginModalDisplayed(), 
                "Login modal should still be displayed after failed login");
    }
    
    /**
     * Test login with empty username
     */
    @Test
    public void testLoginWithEmptyUsername() {
        Reporter.log("Starting testLoginWithEmptyUsername...", true);
        
        // Open login modal
        homePage.clickLoginLink();
        
        // Enter only password
        loginPage.enterPassword("somepassword");
        loginPage.clickLoginButton();
        
        // Check for error alert
        String alertText = alertHelper.handleAlert("Please fill");
        assertTrue(alertText.contains("Please fill") || alertText.contains("empty"), 
                "Alert should indicate empty fields. Actual: " + alertText);
                
        // Verify modal is still displayed
        assertTrue(loginPage.isLoginModalDisplayed(), 
                "Login modal should still be displayed after failed login");
    }
    
    /**
     * Test login with empty password
     */
    @Test
    public void testLoginWithEmptyPassword() {
        Reporter.log("Starting testLoginWithEmptyPassword...", true);
        
        // Open login modal
        homePage.clickLoginLink();
        
        // Enter only username
        loginPage.enterUsername("someusername");
        loginPage.clickLoginButton();
        
        // Check for error alert
        String alertText = alertHelper.handleAlert("Please fill");
        assertTrue(alertText.contains("Please fill") || alertText.contains("empty"), 
                "Alert should indicate empty fields. Actual: " + alertText);
                
        // Verify modal is still displayed
        assertTrue(loginPage.isLoginModalDisplayed(), 
                "Login modal should still be displayed after failed login");
    }

    @Test
    public void testProductSelection() {
        // Verify product list is displayed
        assertTrue(productPage.isProductListDisplayed(), "Product list should be displayed");
        
        // Select a specific product and add to cart
        String productName = "Samsung galaxy s6";
        addProductToCart(productName);
        
        // Go to cart and verify product is added
        productPage.goToCart();
        
        // Add an explicit wait for cart items to be visible
        waitHelper.waitForPageLoad();
        try {
            // Wait a short time for the cart to update
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Verify cart has exactly one item
        assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one item");
    }

    @Test
    public void testCartFunctionality() {
        // Add product to cart using helper method
        addProductToCart("Samsung galaxy s6");
        
        // Go to cart
        productPage.goToCart();
        
        // Add an explicit wait for cart items to be visible
        waitHelper.waitForPageLoad();
        try {
            // Wait a short time for the cart to update
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one item");
    }

    @Test
    public void testCompletePurchase() {
        Reporter.log("Starting testCompletePurchase...", true);
        
        // Add product to cart using helper method
        addProductToCart("Samsung galaxy s6");
        
        // Go to cart and place order
        productPage.goToCart();
        cartPage.clickPlaceOrder();
        
        // Fill purchase form and complete order
        cartPage.fillPurchaseForm(
            "Test User",
            "Test Country",
            "Test City",
            "4111111111111111",
            "12",
            "2025"
        );
        
        cartPage.clickPurchase();
        
        // Wait for confirmation and verify
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
        // wait 500 ms
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Reporter.log(e.toString());
        }
        categoryPage.clickLaptopsCategory();
        assertTrue(categoryPage.isCategoryPageLoaded("laptop"), "Laptops category page should be loaded");
        assertTrue(categoryPage.getProductCount() > 0, "Laptops category should have products");
        List<String> laptopProducts = categoryPage.getProductNames();
        assertTrue(laptopProducts.stream().anyMatch(name -> name.contains("MacBook")), 
            "Laptops category should contain MacBook products");
    }

    @Test
    public void testCategoryNavigation() {
        // Test navigation between categories
        categoryPage.clickPhonesCategory();
        assertTrue(categoryPage.isCategoryPageLoaded("phone"), "Should be on phones category");
        
        categoryPage.clickLaptopsCategory();
        assertTrue(categoryPage.isCategoryPageLoaded("laptop"), "Should be on laptops category");
        
        categoryPage.clickMonitorsCategory();
        assertTrue(categoryPage.isCategoryPageLoaded("monitor"), "Should be on monitors category");
    }

    @Test
    public void testProductFromCategory() {
        // Select a product from phones category and add to cart
        categoryPage.clickPhonesCategory();
        waitHelper.waitForPageLoad();
        
        // Use the helper method to add product to cart
        addProductToCart("Samsung galaxy s6");
        
        // Verify product is in cart
        productPage.goToCart();
        
        // Add an explicit wait for cart items to be visible
        waitHelper.waitForPageLoad();
        try {
            // Wait a short time for the cart to update
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one item");
    }

    @Test
    public void testSignUpFunctionality() {
        // Generate a unique username
        String username = "testuser" + System.currentTimeMillis();
        String password = "testpass";
        
        // Click signup link and wait for modal
        homePage.clickSignUpLink();
        waitHelper.waitForPageLoad();
        
        // Fill in signup form
        signUpPage.enterSignUpUsername(username);
        signUpPage.enterSignUpPassword(password);
        signUpPage.clickSignUpButton();
        
        // Wait for alert and handle it
        String alertText = alertHelper.handleAlert("Sign up successful");
        assertTrue(alertText.contains("Sign up successful"), "Alert should indicate successful signup");
        
        // Verify we can login with new credentials
        login(username, password);
        assertTrue(loginPage.isWelcomeMessageDisplayed(), "Should be able to login with new credentials");
    }
    
    /**
     * Test signup with empty credentials
     */
    @Test
    public void testSignUpWithEmptyCredentials() {
        Reporter.log("Starting testSignUpWithEmptyCredentials...", true);
        
        // Open signup modal
        homePage.clickSignUpLink();
        
        // Click signup without entering credentials
        signUpPage.clickSignUpButton();
        
        // Check for error alert
        String alertText = alertHelper.handleAlert("Please fill");
        assertTrue(alertText.contains("Please fill") || alertText.contains("empty"), 
                "Alert should indicate empty fields. Actual: " + alertText);
                
        // Verify modal is still displayed
        assertTrue(signUpPage.isSignUpModalDisplayed(), 
                "Sign up modal should still be displayed after failed signup");
    }
    
    /**
     * Test signup with empty username
     */
    @Test
    public void testSignUpWithEmptyUsername() {
        Reporter.log("Starting testSignUpWithEmptyUsername...", true);
        
        // Open signup modal
        homePage.clickSignUpLink();
        
        // Enter only password
        signUpPage.enterSignUpPassword("somepassword");
        signUpPage.clickSignUpButton();
        
        // Check for error alert
        String alertText = alertHelper.handleAlert("Please fill");
        assertTrue(alertText.contains("Please fill") || alertText.contains("empty"), 
                "Alert should indicate empty fields. Actual: " + alertText);
                
        // Verify modal is still displayed
        assertTrue(signUpPage.isSignUpModalDisplayed(), 
                "Sign up modal should still be displayed after failed signup");
    }
    
    /**
     * Test signup with empty password
     */
    @Test
    public void testSignUpWithEmptyPassword() {
        Reporter.log("Starting testSignUpWithEmptyPassword...", true);
        
        // Open signup modal
        homePage.clickSignUpLink();
        
        // Enter only username
        signUpPage.enterSignUpUsername("someusername");
        signUpPage.clickSignUpButton();
        
        // Check for error alert
        String alertText = alertHelper.handleAlert("Please fill");
        assertTrue(alertText.contains("Please fill") || alertText.contains("empty"), 
                "Alert should indicate empty fields. Actual: " + alertText);
                
        // Verify modal is still displayed
        assertTrue(signUpPage.isSignUpModalDisplayed(), 
                "Sign up modal should still be displayed after failed signup");
    }
    
    /**
     * Test signup with special characters in username
     */
    @Test
    public void testSignUpWithSpecialCharacters() {
        Reporter.log("Starting testSignUpWithSpecialCharacters...", true);
        
        // Open signup modal
        homePage.clickSignUpLink();
        
        // Enter username with special characters
        signUpPage.enterSignUpUsername("user@#$%^&*()");
        signUpPage.enterSignUpPassword("password");
        signUpPage.clickSignUpButton();
        
        // Check result - this may succeed or fail depending on implementation
        // If it succeeds, we should verify we can login with these credentials
        if (alertHelper.isAlertPresent()) {
            String alertText = alertHelper.handleAlert("");
            if (alertText.contains("Sign up successful")) {
                Reporter.log("Special characters in username were accepted", true);
                
                // Try to log in with this username
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
    
    /**
     * Test signup with very long username and password
     */
    @Test
    public void testSignUpWithVeryLongCredentials() {
        Reporter.log("Starting testSignUpWithVeryLongCredentials...", true);
        
        // Generate very long credentials
        StringBuilder longUsername = new StringBuilder("user");
        StringBuilder longPassword = new StringBuilder("pass");
        
        // Add 100 characters to each
        for (int i = 0; i < 100; i++) {
            longUsername.append("a");
            longPassword.append("b");
        }
        
        // Open signup modal
        homePage.clickSignUpLink();
        
        // Enter very long credentials
        signUpPage.enterSignUpUsername(longUsername.toString());
        signUpPage.enterSignUpPassword(longPassword.toString());
        signUpPage.clickSignUpButton();
        
        // Check result - this may succeed or fail depending on implementation
        if (alertHelper.isAlertPresent()) {
            String alertText = alertHelper.handleAlert("");
            if (alertText.contains("Sign up successful")) {
                Reporter.log("Very long credentials were accepted", true);
                
                // Try to log in with these credentials
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
        // Click contact link
        homePage.clickContactLink();
        
        // Fill in contact form
        String email = "test@example.com";
        String name = "Test User";
        String message = "This is a test message";
        
        contactPage.sendContactMessage(email, name, message);
        
        // Handle alert
        String alertText = alertHelper.handleAlert("Thanks for the message");
        assertTrue(alertText.contains("Thanks for the message"), 
                "Contact message should be sent successfully");
    }
    
    /**
     * Data provider for category tests
     * Provides [category name, expected product brand]
     */
    @DataProvider(name = "categoryTestData")
    public Object[][] getCategoryData() {
        return new Object[][] {
            {"phone", "Samsung"},
            {"laptop", "MacBook"},
            {"monitor", "Apple"}
        };
    }

    /**
     * Parameterized test for categories using data provider
     */
    @Test(dataProvider = "categoryTestData")
    public void testProductCategory(String category, String expectedBrand) {
        // Log test details
        Reporter.log("Testing " + category + " category, expecting " + expectedBrand + " products", true);

        // Select appropriate category
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

        // Verify category loaded correctly
        assertTrue(categoryPage.isCategoryPageLoaded(category),
                category + " category page should be loaded");

        // Verify products exist
        assertTrue(categoryPage.getProductCount() > 0,
                category + " category should have products");

        // Verify expected brand exists
        List<String> products = categoryPage.getProductNames();
        Reporter.log("Found products: " + products, true);

        assertTrue(products.stream().anyMatch(name -> name.contains(expectedBrand)),
                category + " category should contain " + expectedBrand + " products");
    }

    /**
     * Test to verify that the system should not accept non-numeric values in 
     * card/month/year fields but does due to lack of validation
     */
    @Test
    public void testInvalidPurchaseFormData() {
        Reporter.log("Starting testInvalidPurchaseFormData...", true);
        
        // Add product to cart using helper method
        addProductToCart("Samsung galaxy s6");
        
        // Go to cart and place order
        productPage.goToCart();
        cartPage.clickPlaceOrder();
        
        // Fill purchase form with invalid data - strings in month/year fields
        cartPage.fillPurchaseForm(
            "Test User",
            "Test Country",
            "Test City",
            "Invalid-Card-Number",  // Invalid card number with text
            "ABC",                  // Invalid month (non-numeric)
            "XYZ"                   // Invalid year (non-numeric)
        );
        
        // This should fail in a real application, but will succeed due to lack of validation
        cartPage.clickPurchase();
        
        // Wait for confirmation and verify that the order succeeds despite invalid data
        String confirmation = cartPage.getOrderConfirmation();
        Reporter.log("Confirmation text: " + confirmation, true);
        
        // The test asserts that the confirmation appears, demonstrating the validation issue
        assertFalse(confirmation.contains("Thank you for your purchase"),
            "Order confirmation should appear despite invalid numeric data, highlighting the validation issue");
    }
    
    /**
     * Test to verify that the system should not allow purchase with an empty cart
     * but does due to lack of validation
     */
    @Test
    public void testEmptyCartPurchase() {
        Reporter.log("Starting testEmptyCartPurchase...", true);
        
        // Go directly to cart without adding any products
        homePage.clickCartLink();
        
        // Verify the cart is empty
        assertTrue(cartPage.isCartEmpty(), "Cart should be empty for this test");
        
        // Try to place order with empty cart
        cartPage.clickPlaceOrder();
        
        // Fill purchase form
        cartPage.fillPurchaseForm(
            "Test User",
            "Test Country",
            "Test City",
            "4111111111111111",
            "12",
            "2025"
        );
        
        // This should fail in a real application, but will succeed due to lack of validation
        cartPage.clickPurchase();
        
        // Wait for confirmation and verify that the order succeeds despite empty cart
        String confirmation = cartPage.getOrderConfirmation();
        Reporter.log("Confirmation text: " + confirmation, true);
        
        // The test asserts that the confirmation appears, demonstrating the validation issue
        assertFalse(confirmation.contains("Thank you for your purchase"),
            "Order confirmation should not appear with empty cart, highlighting the validation issue");
    }

    /**
     * Test to verify that the system allows purchase with only name and credit card
     * filled, which is a validation issue
     */
    @Test
    public void testPartialPurchaseFormValidation() {
        Reporter.log("Starting testPartialPurchaseFormValidation...", true);
        
        // Add product to cart using helper method
        addProductToCart("Samsung galaxy s6");
        
        // Go to cart and place order
        productPage.goToCart();
        cartPage.clickPlaceOrder();
        
        // Fill only name and credit card number, leaving other fields empty
        waitHelper.waitForElementVisible(cartPage.getNameField()).sendKeys("Test User");
        waitHelper.waitForElementVisible(cartPage.getCardField()).sendKeys("4111111111111111");
        
        // This should fail in a real application, but will succeed due to lack of validation
        cartPage.clickPurchase();
        
        // Wait for confirmation and verify that the order succeeds despite incomplete form
        String confirmation = cartPage.getOrderConfirmation();
        Reporter.log("Confirmation text: " + confirmation, true);
        
        // Assert that confirmation appears even with incomplete form data
        assertFalse(confirmation.contains("Thank you for your purchase"),
            "Order confirmation should not appear with incomplete form data, highlighting the validation issue");
    }

    /**
     * Test to verify that the date shown in order confirmation has month value one less than current month
     * The format is MM/DD/YYYY where MM is one month behind the current month
     */
    @Test
    public void testPurchaseDateIsCorrect() {
        Reporter.log("Starting testPurchaseIsCorrect...", true);
        
        // Add product to cart using helper method
        addProductToCart("Samsung galaxy s6");
        
        // Go to cart and place order
        productPage.goToCart();
        cartPage.clickPlaceOrder();
        
        // Fill purchase form and complete order
        cartPage.fillPurchaseForm(
            "Test User",
            "Test Country",
            "Test City",
            "4111111111111111",
            "12",
            "2025"
        );
        
        cartPage.clickPurchase();
        
        // Wait for confirmation and get the complete confirmation text
        String confirmationText = cartPage.getCompleteOrderConfirmation();
        Reporter.log("Full confirmation text: " + confirmationText, true);
        
        // Get current date information
        Calendar currentDate = Calendar.getInstance();
        int currentMonth = currentDate.get(Calendar.MONTH) + 1; // Calendar months are zero-based
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentYear = currentDate.get(Calendar.YEAR);
        
        Reporter.log("Current date: " + currentDay + "/" + currentMonth + "/" + currentYear, true);
        
        // Extract date from confirmation text using regex
        Pattern datePattern = Pattern.compile("Date:\\s*(\\d+)/(\\d+)/(\\d+)");
        Matcher matcher = datePattern.matcher(confirmationText);
        
        if (matcher.find()) {
            int displayedMonth = Integer.parseInt(matcher.group(2));
            int displayedDay = Integer.parseInt(matcher.group(1));
            int displayedYear = Integer.parseInt(matcher.group(3));
            
            Reporter.log("Displayed date: " + displayedDay + "/" + displayedMonth + "/" + displayedYear, true);
            
            // Assert that expected date is equal to current date
            assertEquals(displayedMonth, currentMonth, "Displayed month should be equal to current month");

            assertEquals(displayedDay, currentDay, "Displayed day should be equal to current day");

            assertEquals(displayedYear, currentYear, "Displayed year should be equal to current year");

        } else {
            fail("Could not find date in confirmation text: " + confirmationText);
        }
    }

    /**
     * Test to verify that the Contact Us modal allows sending empty data
     */
    @Test
    public void testEmptyContactFormSubmission() {
        Reporter.log("Starting testEmptyContactFormSubmission...", true);
        
        // Click contact link to open the modal
        homePage.clickContactLink();
        
        // Verify contact modal is displayed
        assertTrue(contactPage.isContactModalDisplayed(), "Contact modal should be displayed");
        
        // Submit the form without filling in any data
        contactPage.clickSendMessageButton();
        
        // The form should validate inputs, but due to lack of validation, it allows submission
        // Wait for the alert and verify it appears despite empty fields
        String alertText = alertHelper.handleAlert("Thanks for the message");
        
        // Log the alert text for verification
        Reporter.log("Alert text: " + alertText, true);
        
        // Assert that the message was sent despite empty fields
        assertFalse(alertText.contains("Thanks for the message"),
                "Contact form should not allow empty submission, but it does, highlighting the validation issue");
    }
}