package com.demoblaze.pages;

import com.demoblaze.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class SignUpPage extends BasePage {

    @FindBy(id = "sign-username")
    private WebElement usernameField;

    @FindBy(id = "sign-password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(text(),'Sign up')]")
    private WebElement signUpButton;
    
    @FindBy(id = "signInModal")
    private WebElement signUpModal;
    
    @FindBy(xpath = "//div[@id='signInModal']//button[contains(@class,'close')]")
    private WebElement closeButton;

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public void enterSignUpUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameField)).sendKeys(username);
    }

    public void enterSignUpPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordField)).sendKeys(password);
    }

    public void clickSignUpButton() {
        wait.until(ExpectedConditions.elementToBeClickable(signUpButton)).click();
    }

    public void signUp(String username, String password) {
        enterSignUpUsername(username);
        enterSignUpPassword(password);
        clickSignUpButton();
    }
    
    public boolean isSignUpModalDisplayed() {
        try {
            return waitHelper.waitForElementVisible(signUpModal).isDisplayed();
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