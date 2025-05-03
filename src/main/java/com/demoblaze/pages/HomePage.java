package com.demoblaze.pages;

import com.demoblaze.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    @FindBy(id = "login2")
    private WebElement loginLink;

    @FindBy(id = "signin2")
    private WebElement signUpLink;

    @FindBy(id = "cartur")
    private WebElement cartLink;

    @FindBy(linkText = "Contact")
    private WebElement contactLink;
    
    @FindBy(linkText = "Home")
    private WebElement homeLink;

    @FindBy(linkText = "Phones")
    private WebElement phonesCategory;

    @FindBy(linkText = "Laptops")
    private WebElement laptopsCategory;

    @FindBy(linkText = "Monitors")
    private WebElement monitorsCategory;

    @FindBy(css = ".card-title a")
    private WebElement productList;

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickLoginLink() {
        waitHelper.waitForElementToBeClickable(loginLink).click();
    }

    public void clickSignUpLink() {
        waitHelper.waitForElementToBeClickable(signUpLink).click();
    }

    public void clickCartLink() {
        waitHelper.waitForElementToBeClickable(cartLink).click();
    }

    public void clickContactLink() {
        waitHelper.waitForElementToBeClickable(contactLink).click();
    }
    
    public void clickHomeLink() {
        waitHelper.waitForElementToBeClickable(homeLink).click();
        waitHelper.waitForPageLoad();
    }

    public void clickPhonesCategory() {
        waitHelper.waitForElementToBeClickable(phonesCategory).click();
    }

    public void clickLaptopsCategory() {
        waitHelper.waitForElementToBeClickable(laptopsCategory).click();
    }

    public void clickMonitorsCategory() {
        waitHelper.waitForElementToBeClickable(monitorsCategory).click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isLoginLinkDisplayed() {
        return waitHelper.waitForElementVisible(loginLink).isDisplayed();
    }

    public boolean isSignUpLinkDisplayed() {
        return waitHelper.waitForElementVisible(signUpLink).isDisplayed();
    }

    public boolean isCartLinkDisplayed() {
        return waitHelper.waitForElementVisible(cartLink).isDisplayed();
    }

    public boolean isContactLinkDisplayed() {
        return waitHelper.waitForElementVisible(contactLink).isDisplayed();
    }

    public boolean isPhonesCategoryDisplayed() {
        return waitHelper.waitForElementVisible(phonesCategory).isDisplayed();
    }

    public boolean isLaptopsCategoryDisplayed() {
        return waitHelper.waitForElementVisible(laptopsCategory).isDisplayed();
    }

    public boolean isMonitorsCategoryDisplayed() {
        return waitHelper.waitForElementVisible(monitorsCategory).isDisplayed();
    }

    public boolean isProductListDisplayed() {
        return waitHelper.waitForElementVisible(productList).isDisplayed();
    }
}