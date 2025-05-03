package com.demoblaze.pages;

import com.demoblaze.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ContactPage extends BasePage {

    @FindBy(linkText = "Contact")
    private WebElement contactLink;

    @FindBy(id = "recipient-email")
    private WebElement contactEmailField;

    @FindBy(id = "recipient-name")
    private WebElement contactNameField;

    @FindBy(id = "message-text")
    private WebElement messageField;

    @FindBy(xpath = "//button[contains(text(),'Send message')]")
    private WebElement sendMessageButton;

    public ContactPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickContactLink() {
        wait.until(ExpectedConditions.elementToBeClickable(contactLink)).click();
    }

    public void enterContactEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(contactEmailField)).sendKeys(email);
    }

    public void enterContactName(String name) {
        wait.until(ExpectedConditions.visibilityOf(contactNameField)).sendKeys(name);
    }

    public void enterMessage(String message) {
        wait.until(ExpectedConditions.visibilityOf(messageField)).sendKeys(message);
    }

    public void clickSendMessageButton() {
        wait.until(ExpectedConditions.elementToBeClickable(sendMessageButton)).click();
    }

    public void sendContactMessage(String email, String name, String message) {
        enterContactEmail(email);
        enterContactName(name);
        enterMessage(message);
        clickSendMessageButton();
    }
} 