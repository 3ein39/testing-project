package com.demoblaze.pages;

import com.demoblaze.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SignUpPage extends BasePage {

    @FindBy(id = "sign-username")
    private WebElement usernameField;

    @FindBy(id = "sign-password")
    private WebElement passwordField;

    @FindBy(xpath = "//button[contains(text(),'Sign up')]")
    private WebElement signUpButton;

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
} 