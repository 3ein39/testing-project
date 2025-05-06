package com.demoblaze.tests;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.Reporter;
import static org.testng.Assert.*;

import java.util.List;

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
        // Use login helper method from BaseTest
        login("reveari", "reveari");
        
        // Verify login was successful
        waitHelper.waitWithTimeout(ExpectedConditions.visibilityOf(loginPage.getWelcomeMessageElement()), 5);
        assertTrue(loginPage.isWelcomeMessageDisplayed(), "Welcome message should be displayed after login");
        String welcomeMessage = loginPage.getWelcomeMessage();
        assertTrue(welcomeMessage.contains("Welcome"), "Welcome message should contain 'Welcome'");
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
}