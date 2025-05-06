package com.demoblaze.tests;

import com.demoblaze.pages.*;
import com.demoblaze.utils.AlertHelper;
import com.demoblaze.utils.WaitHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.ITestResult;
import java.lang.reflect.Method;
import org.testng.Reporter;

/**
 * Base test class that provides common setup and teardown for all tests
 */
public class BaseTest {
    protected static WebDriver driver;
    protected HomePage homePage;
    protected LoginPage loginPage;
    protected ProductPage productPage;
    protected CartPage cartPage;
    protected CategoryPage categoryPage;
    protected SignUpPage signUpPage;
    protected ContactPage contactPage;
    protected WaitHelper waitHelper;
    protected AlertHelper alertHelper;
    
    @BeforeClass
    public void setUpBrowser() {
        Reporter.log("Setting up browser for test class", true);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void setUp(Method method) {
        Reporter.log("Starting test: " + method.getName(), true);
        
        // Initialize helper classes using the shared driver
        waitHelper = new WaitHelper(driver);
        alertHelper = new AlertHelper(driver);
        
        // Initialize page objects using the shared driver
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        categoryPage = new CategoryPage(driver);
        signUpPage = new SignUpPage(driver);
        contactPage = new ContactPage(driver);
        
        // Navigate to the website to start fresh
        homePage.navigateTo("https://www.demoblaze.com/");
        
        // Clear cookies and storage to reset application state
        clearBrowserState();
    }
    
    /**
     * Clear browser state between tests to ensure test isolation
     */
    private void clearBrowserState() {
        // Delete all cookies
        driver.manage().deleteAllCookies();
        
        // Clear localStorage and sessionStorage
        try {
            // Execute JavaScript to clear storage
            if (driver != null) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("window.localStorage.clear();");
                jsExecutor.executeScript("window.sessionStorage.clear();");
                
                // Perform a hard reload to completely refresh the page state
                jsExecutor.executeScript("location.reload(true);");
                
                // Wait for the page to reload completely
                waitHelper.waitForPageLoad();
            }
        } catch (Exception e) {
            Reporter.log("Error clearing browser storage: " + e.getMessage(), true);
        }
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        Reporter.log("Test " + result.getName() + " completed with status: " + 
                (result.isSuccess() ? "PASSED" : "FAILED"), true);
    }
    
    @AfterClass
    public void tearDown() {
        Reporter.log("Tearing down browser after completing all tests", true);
        
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    
    /**
     * Helper method to handle common login flow
     */
    protected void login(String username, String password) {
        homePage.clickLoginLink();
        waitHelper.waitForPageLoad();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        
        // Handle alert if it appears
        if (alertHelper.isAlertPresent()) {
            alertHelper.acceptAlertIfPresent();
        } else {
            waitHelper.waitForPageLoad();
        }
    }
    
    /**
     * Helper method to add a product to cart by name
     */
    protected void addProductToCart(String productName) {
        productPage.clickProductByName(productName);
        waitHelper.waitForPageLoad();
        productPage.addToCart();
        alertHelper.acceptAlertIfPresent();
    }
}