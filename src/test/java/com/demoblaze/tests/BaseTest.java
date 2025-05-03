package com.demoblaze.tests;

import com.demoblaze.pages.*;
import com.demoblaze.utils.AlertHelper;
import com.demoblaze.utils.WaitHelper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.ITestResult;
import java.lang.reflect.Method;
import org.testng.Reporter;

/**
 * Base test class that provides common setup and teardown for all tests
 */
public class BaseTest {
    protected WebDriver driver;
    protected HomePage homePage;
    protected LoginPage loginPage;
    protected ProductPage productPage;
    protected CartPage cartPage;
    protected CategoryPage categoryPage;
    protected SignUpPage signUpPage;
    protected ContactPage contactPage;
    protected WaitHelper waitHelper;
    protected AlertHelper alertHelper;

    @BeforeMethod
    public void setUp(Method method) {
        Reporter.log("Starting test: " + method.getName(), true);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        
        // Initialize helper classes
        waitHelper = new WaitHelper(driver);
        alertHelper = new AlertHelper(driver);
        
        // Initialize page objects
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        categoryPage = new CategoryPage(driver);
        signUpPage = new SignUpPage(driver);
        contactPage = new ContactPage(driver);
        
        // Navigate to the website
        homePage.navigateTo("https://www.demoblaze.com/");
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        Reporter.log("Test " + result.getName() + " completed with status: " + 
                (result.isSuccess() ? "PASSED" : "FAILED"), true);
        
        if (driver != null) {
            driver.quit();
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