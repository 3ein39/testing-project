package com.demoblaze.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class AlertHelper {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public AlertHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String handleAlert(String expectedText) {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            if (expectedText != null && !expectedText.isEmpty()) {
                assertTrue(alertText.contains(expectedText), 
                    "Alert text should contain '" + expectedText + "', found: '" + alertText + "'");
            }
            alert.accept();
            return alertText;
        } catch (Exception e) {
            fail("Alert handling failed: " + e.getMessage());
            return null;
        }
    }

    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
    
    public boolean acceptAlertIfPresent() {
        try {
            wait.until(ExpectedConditions.alertIsPresent()).accept();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}