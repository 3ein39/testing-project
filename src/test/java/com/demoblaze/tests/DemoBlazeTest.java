package com.demoblaze.tests;

import com.demoblaze.pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;

import static org.testng.Assert.*;
import java.util.List;

public class DemoBlazeTest {
    private WebDriver driver;
    private HomePage homePage;
    private LoginPage loginPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private CategoryPage categoryPage;
    private SignUpPage signUpPage;
    private ContactPage contactPage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        categoryPage = new CategoryPage(driver);
        signUpPage = new SignUpPage(driver);
        contactPage = new ContactPage(driver);
        homePage.navigateTo("https://www.demoblaze.com/");
    }

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
        // Click login link and wait for modal
        homePage.clickLoginLink();
        try {
            Thread.sleep(1000); // Wait for modal to appear
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Enter login credentials (using demo account)
        loginPage.enterUsername("demo");
        loginPage.enterPassword("demo");
        loginPage.clickLoginButton();
        
        // Handle alert if it appears
        try {
            Thread.sleep(1000); // Wait for alert
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (alertText.contains("Wrong password")) {
                fail("Login failed with wrong credentials");
            }
            alert.accept();
        } catch (NoAlertPresentException e) {
            // No alert is fine, means login was successful
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Wait for welcome message
        try {
            Thread.sleep(2000); // Wait for login to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Verify login was successful
        assertTrue(loginPage.isWelcomeMessageDisplayed(), "Welcome message should be displayed after login");
        String welcomeMessage = loginPage.getWelcomeMessage();
        assertTrue(welcomeMessage.contains("Welcome"), "Welcome message should contain 'Welcome'");
    }

    @Test
    public void testProductSelection() {
        // Verify product list is displayed
        assertTrue(productPage.isProductListDisplayed(), "Product list should be displayed");
        
        // Select a specific product
        String productName = "Samsung galaxy s6";
        productPage.clickProductByName(productName);
        
        try {
            Thread.sleep(1000); // Wait for product details to load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Add product to cart
        productPage.addToCart();
        
        // Handle alert
        try {
            Thread.sleep(1000); // Wait for alert
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            assertTrue(alertText.contains("Product added"), "Product should be added to cart");
            alert.accept();
        } catch (NoAlertPresentException e) {
            fail("Expected alert was not present");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Go to cart and verify product is added
        productPage.goToCart();
        assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one item");
    }

    @Test
    public void testCartFunctionality() {
        // Add product to cart
        productPage.clickProductByName("Samsung galaxy s6");
        productPage.addToCart();
        
        try {
            Thread.sleep(2000); // Increased wait time for alert
            Alert alert = driver.switchTo().alert();
            alert.accept();
        } catch (Exception e) {
            fail("Failed to handle alert: " + e.getMessage());
        }
        
        // Go to cart
        productPage.goToCart();
        assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one item");
    }

    @Test
    public void testCompletePurchase() {
        System.out.println("Starting testCompletePurchase...");
        
        // Add product to cart
        System.out.println("Adding product to cart...");
        productPage.clickProductByName("Samsung galaxy s6");
        productPage.addToCart();
        
        try {
            System.out.println("Waiting for add to cart alert...");
            Thread.sleep(2000); // Wait for alert
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText());
            alert.accept();
        } catch (Exception e) {
            System.out.println("Error handling add to cart alert: " + e.getMessage());
            fail("Failed to handle alert: " + e.getMessage());
        }
        
        // Go to cart and place order
        System.out.println("Navigating to cart...");
        productPage.goToCart();
        
        System.out.println("Clicking place order...");
        cartPage.clickPlaceOrder();
        
        try {
            System.out.println("Filling purchase form...");
            Thread.sleep(2000); // Wait for modal to appear
            cartPage.fillPurchaseForm(
                "Test User",
                "Test Country",
                "Test City",
                "4111111111111111",
                "12",
                "2025"
            );
            
            System.out.println("Clicking purchase button...");
            cartPage.clickPurchase();
            
            // Wait for confirmation and verify
            System.out.println("Getting order confirmation...");
            String confirmation = cartPage.getOrderConfirmation();
            System.out.println("Confirmation text: " + confirmation);
            
            assertTrue(confirmation.contains("Thank you for your purchase"), 
                "Order confirmation message should be displayed. Actual message: " + confirmation);
        } catch (InterruptedException e) {
            System.out.println("Test interrupted: " + e.getMessage());
            fail("Test interrupted: " + e.getMessage());
        }
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
    public void testMonitorsCategory() {
        categoryPage.clickMonitorsCategory();
        List<String> monitorProducts = categoryPage.getProductNames();
        System.out.println("Monitor products: " + monitorProducts);
        assertTrue(categoryPage.isCategoryPageLoaded("monitor"), "Monitors category should be loaded");
        assertTrue(categoryPage.getProductCount() > 0, "Monitors category should have products");
        assertTrue(monitorProducts.stream().anyMatch(name -> name.contains("Monitor") || 
                                                          name.contains("ASUS") || 
                                                          name.contains("Apple")), 
            "Monitors category should contain monitor products");
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
        try {
            Thread.sleep(1000); // Wait for category to load
            productPage.clickProductByName("Samsung galaxy s6");
            productPage.addToCart();
            
            Thread.sleep(2000); // Wait for alert
            Alert alert = driver.switchTo().alert();
            alert.accept();
            
            // Verify product is in cart
            productPage.goToCart();
            assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one item");
        } catch (InterruptedException e) {
            fail("Test interrupted: " + e.getMessage());
        }
    }

    @Test
    public void testSignUpFunctionality() {
        // Generate a unique username
        String username = "testuser" + System.currentTimeMillis();
        String password = "testpass";
        
        // Click signup link and wait for modal
        homePage.clickSignUpLink();
        try {
            Thread.sleep(1000); // Wait for modal to appear
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Fill in signup form
        signUpPage.enterSignUpUsername(username);
        signUpPage.enterSignUpPassword(password);
        signUpPage.clickSignUpButton();
        
        // Wait for alert and handle it
        try {
            Thread.sleep(1000); // Wait for alert to appear
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            assertTrue(alertText.contains("Sign up successful"), "Alert should indicate successful signup");
            alert.accept();
        } catch (NoAlertPresentException e) {
            fail("Expected alert was not present");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Verify we can login with new credentials
        homePage.clickLoginLink();
        try {
            Thread.sleep(1000); // Wait for login modal
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
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
        try {
            Thread.sleep(1000); // Wait for alert
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            assertTrue(alertText.contains("Thanks for the message"), "Contact message should be sent successfully");
            alert.accept();
        } catch (NoAlertPresentException e) {
            fail("Expected alert was not present");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
} 