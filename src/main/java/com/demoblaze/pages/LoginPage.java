package com.demoblaze.pages;

import com.demoblaze.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class LoginPage extends BasePage {

    @FindBy(id = "loginusername")
    private WebElement usernameField;

    @FindBy(id = "loginpassword")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(text(),'Log in')]")
    private WebElement loginButton;

    @FindBy(id = "nameofuser")
    private WebElement welcomeMessage;
    
    @FindBy(id = "logInModal")
    private WebElement loginModal;
    
    @FindBy(xpath = "//div[@id='logInModal']//button[contains(@class,'close')]")
    private WebElement closeButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        waitHelper.waitForElementVisible(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        waitHelper.waitForElementVisible(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        waitHelper.waitForElementToBeClickable(loginButton).click();
    }

    public boolean isWelcomeMessageDisplayed() {
        try {
            return waitHelper.waitForElementVisible(welcomeMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getWelcomeMessage() {
        return waitHelper.waitForElementVisible(welcomeMessage).getText();
    }
    
    /**
     * Gets the welcome message element for use in wait conditions
     * @return the welcome message WebElement
     */
    public WebElement getWelcomeMessageElement() {
        return welcomeMessage;
    }
    
    /**
     * Gets the login button element for use in wait conditions
     * @return the login button WebElement
     */
    public WebElement getLoginButton() {
        return loginButton;
    }
    
    /**
     * Checks if login modal is displayed
     * @return true if modal is displayed, false otherwise
     */
    public boolean isLoginModalDisplayed() {
        try {
            return waitHelper.waitForElementVisible(loginModal).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Checks if username field is displayed
     * @return true if field is displayed, false otherwise
     */
    public boolean isUsernameFieldDisplayed() {
        try {
            return waitHelper.waitForElementVisible(usernameField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Checks if password field is displayed
     * @return true if field is displayed, false otherwise
     */
    public boolean isPasswordFieldDisplayed() {
        try {
            return waitHelper.waitForElementVisible(passwordField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Checks if login button is displayed
     * @return true if button is displayed, false otherwise
     */
    public boolean isLoginButtonDisplayed() {
        try {
            return waitHelper.waitForElementVisible(loginButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Presses the Escape key to close modal
     */
    public void pressEscapeKey() {
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }
    
    /**
     * Clicks the close button to close the modal
     */
    public void clickCloseButton() {
        waitHelper.waitForElementToBeClickable(closeButton).click();
    }
}