package com.demoblaze.pages;

import com.demoblaze.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    @FindBy(id = "loginusername")
    private WebElement usernameField;

    @FindBy(id = "loginpassword")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(text(),'Log in')]")
    private WebElement loginButton;

    @FindBy(id = "nameofuser")
    private WebElement welcomeMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public boolean isWelcomeMessageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(welcomeMessage)).isDisplayed();
    }

    public String getWelcomeMessage() {
        return wait.until(ExpectedConditions.visibilityOf(welcomeMessage)).getText();
    }
} 