package com.demoblaze.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Utility class to handle various wait operations consistently
 */
public class WaitHelper {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final int defaultTimeout = 10; // seconds

    public WaitHelper(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(defaultTimeout));
    }

    /**
     * Wait for an element to be clickable
     */
    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for an element to be clickable
     */
    public WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for an element to be visible
     */
    public WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for an element to be visible
     */
    public WebElement waitForElementVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Wait for a list of elements to be visible
     */
    public List<WebElement> waitForAllElementsVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    /**
     * Wait for a list of elements to be visible
     */
    public List<WebElement> waitForAllElementsVisible(List<WebElement> elements) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    /**
     * Wait for page to load completely
     */
    public void waitForPageLoad() {
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * Wait for a specific condition with a custom message
     */
    public <T> T waitFor(ExpectedCondition<T> condition, String errorMessage) {
        try {
            return wait.until(condition);
        } catch (TimeoutException e) {
            throw new TimeoutException(errorMessage, e);
        }
    }

    /**
     * Wait for an element to be stale (no longer attached to DOM)
     */
    public boolean waitForStaleness(WebElement element) {
        return wait.until(ExpectedConditions.stalenessOf(element));
    }

    /**
     * Custom wait with specified timeout in seconds
     */
    public <T> T waitWithTimeout(ExpectedCondition<T> condition, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(condition);
    }
}