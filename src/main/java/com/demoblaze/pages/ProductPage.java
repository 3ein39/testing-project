package com.demoblaze.pages;

import com.demoblaze.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import org.openqa.selenium.StaleElementReferenceException;

public class ProductPage extends BasePage {

    @FindBy(css = ".card-title a")
    private List<WebElement> productLinks;

    @FindBy(css = ".btn-success")
    private WebElement addToCartButton;

    @FindBy(id = "cartur")
    private WebElement cartLink;

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public void clickProductByName(String productName) {
        for (int attempts = 0; attempts < 3; attempts++) {
            try {
                waitHelper.waitForPageLoad();
                waitHelper.waitForAllElementsVisible(productLinks);
                
                for (WebElement product : productLinks) {
                    if (product.getText().equals(productName)) {
                        waitHelper.waitForElementToBeClickable(product).click();
                        return;
                    }
                }
                break;
            } catch (StaleElementReferenceException e) {
                if (attempts == 2) throw new RuntimeException("Failed to click product after 3 attempts", e);
                waitHelper.waitForPageLoad();
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    public void addToCart() {
        waitHelper.waitForElementToBeClickable(addToCartButton).click();
    }

    public void goToCart() {
        waitHelper.waitForElementToBeClickable(cartLink).click();
    }

    public boolean isProductListDisplayed() {
        try {
            waitHelper.waitForAllElementsVisible(productLinks);
            return !productLinks.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}