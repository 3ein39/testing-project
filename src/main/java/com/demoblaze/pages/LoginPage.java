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
    
    public WebElement getWelcomeMessageElement() {
        return welcomeMessage;
    }
    
    public WebElement getLoginButton() {
        return loginButton;
    }
    
    public boolean isLoginModalDisplayed() {
        try {
            return waitHelper.waitForElementVisible(loginModal).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isUsernameFieldDisplayed() {
        try {
            return waitHelper.waitForElementVisible(usernameField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isPasswordFieldDisplayed() {
        try {
            return waitHelper.waitForElementVisible(passwordField).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isLoginButtonDisplayed() {
        try {
            return waitHelper.waitForElementVisible(loginButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void pressEscapeKey() {
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }
    
    public void clickCloseButton() {
        waitHelper.waitForElementToBeClickable(closeButton).click();
    }
}