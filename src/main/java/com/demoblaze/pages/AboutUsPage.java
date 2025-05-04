package com.demoblaze.pages;

import com.demoblaze.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class AboutUsPage extends BasePage {

    @FindBy(linkText = "About us")
    private WebElement aboutUsLink;
    
    @FindBy(id = "videoModal")
    private WebElement aboutUsModal;
    
    @FindBy(className = "modal-title")
    private WebElement modalTitle;
    
    @FindBy(xpath = "//div[@id='videoModal']//button[contains(@class,'close')]")
    private WebElement closeButton;

    public AboutUsPage(WebDriver driver) {
        super(driver);
    }
    
    public void clickAboutUsLink() {
        waitHelper.waitForElementToBeClickable(aboutUsLink).click();
    }
    
    public boolean isAboutUsModalDisplayed() {
        try {
            return waitHelper.waitForElementVisible(aboutUsModal).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public String getModalTitle() {
        return waitHelper.waitForElementVisible(modalTitle).getText();
    }
    
    public void pressEscapeKey() {
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
    }
    
    public void clickCloseButton() {
        waitHelper.waitForElementToBeClickable(closeButton).click();
    }
}