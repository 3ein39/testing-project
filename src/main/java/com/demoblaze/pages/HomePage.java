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
        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
    }

    public void clickSignUpLink() {
        wait.until(ExpectedConditions.elementToBeClickable(signUpLink)).click();
    }

    public void clickCartLink() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }

    public void clickContactLink() {
        wait.until(ExpectedConditions.elementToBeClickable(contactLink)).click();
    }

    public void clickPhonesCategory() {
        wait.until(ExpectedConditions.elementToBeClickable(phonesCategory)).click();
    }

    public void clickLaptopsCategory() {
        wait.until(ExpectedConditions.elementToBeClickable(laptopsCategory)).click();
    }

    public void clickMonitorsCategory() {
        wait.until(ExpectedConditions.elementToBeClickable(monitorsCategory)).click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isLoginLinkDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(loginLink)).isDisplayed();
    }

    public boolean isSignUpLinkDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(signUpLink)).isDisplayed();
    }

    public boolean isCartLinkDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(cartLink)).isDisplayed();
    }

    public boolean isContactLinkDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(contactLink)).isDisplayed();
    }

    public boolean isPhonesCategoryDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(phonesCategory)).isDisplayed();
    }

    public boolean isLaptopsCategoryDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(laptopsCategory)).isDisplayed();
    }

    public boolean isMonitorsCategoryDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(monitorsCategory)).isDisplayed();
    }

    public boolean isProductListDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(productList)).isDisplayed();
    }
} 