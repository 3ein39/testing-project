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
        
        waitHelper = new WaitHelper(driver);
        alertHelper = new AlertHelper(driver);
        
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        categoryPage = new CategoryPage(driver);
        signUpPage = new SignUpPage(driver);
        contactPage = new ContactPage(driver);
        
        homePage.navigateTo("https://www.demoblaze.com/");
        
        clearBrowserState();
    }
    
    private void clearBrowserState() {
        driver.manage().deleteAllCookies();
        
        try {
            if (driver != null) {
                JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
                jsExecutor.executeScript("window.localStorage.clear();");
                jsExecutor.executeScript("window.sessionStorage.clear();");
                
                jsExecutor.executeScript("location.reload(true);");
                
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
    
    protected void login(String username, String password) {
        homePage.clickLoginLink();
        waitHelper.waitForPageLoad();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        
        if (alertHelper.isAlertPresent()) {
            alertHelper.acceptAlertIfPresent();
        } else {
            waitHelper.waitForPageLoad();
        }
    }
    
    protected void addProductToCart(String productName) {
        productPage.clickProductByName(productName);
        waitHelper.waitForPageLoad();
        productPage.addToCart();
        alertHelper.acceptAlertIfPresent();
    }
}