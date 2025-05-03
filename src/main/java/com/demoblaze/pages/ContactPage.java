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
    
    @FindBy(id = "exampleModal")
    private WebElement contactModal;

    public ContactPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickContactLink() {
        waitHelper.waitForElementToBeClickable(contactLink).click();
    }

    public void enterContactEmail(String email) {
        waitHelper.waitForElementVisible(contactEmailField).sendKeys(email);
    }

    public void enterContactName(String name) {
        waitHelper.waitForElementVisible(contactNameField).sendKeys(name);
    }

    public void enterMessage(String message) {
        waitHelper.waitForElementVisible(messageField).sendKeys(message);
    }

    public void clickSendMessageButton() {
        waitHelper.waitForElementToBeClickable(sendMessageButton).click();
    }

    public void sendContactMessage(String email, String name, String message) {
        enterContactEmail(email);
        enterContactName(name);
        enterMessage(message);
        clickSendMessageButton();
    }
    
    /**
     * Checks if contact modal is displayed
     * @return true if modal is displayed, false otherwise
     */
    public boolean isContactModalDisplayed() {
        try {
            return waitHelper.waitForElementVisible(contactModal).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}