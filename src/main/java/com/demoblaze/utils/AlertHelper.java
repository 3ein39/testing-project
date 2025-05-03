package com.demoblaze.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

/**
 * Utility class to handle alert-related operations consistently across tests
 */
public class AlertHelper {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public AlertHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Waits for an alert to be present, verifies its text contains expected content, and accepts it
     * 
     * @param expectedText Text that should be contained in the alert
     * @return The actual text from the alert
     */
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
            return null; // This will never execute due to the fail() but needed for compilation
        }
    }

    /**
     * Checks for an alert and returns true if present, false otherwise
     */
    public boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
    
    /**
     * Waits for an alert, accepts it if present, handles gracefully if not
     * 
     * @return true if alert was present and handled, false otherwise
     */
    public boolean acceptAlertIfPresent() {
        try {
            wait.until(ExpectedConditions.alertIsPresent()).accept();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}