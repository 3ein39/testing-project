package com.demoblaze.tests;

import com.demoblaze.pages.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class HomePageTest {
    private WebDriver driver;
    private HomePage homePage;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        homePage.navigateTo("https://www.demoblaze.com/");
    }

    @Test
    public void testHomePageElements() {
        assertTrue(homePage.isLoginLinkDisplayed(), "Login link should be displayed");
    }

    @Test
    public void testNavigationToLogin() {
        homePage.clickLoginLink();
        // Add assertions for login modal
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
} 