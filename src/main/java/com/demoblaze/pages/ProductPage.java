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
                Thread.sleep(1000); // Wait for page to stabilize
                wait.until(ExpectedConditions.visibilityOfAllElements(productLinks));
                for (WebElement product : productLinks) {
                    if (product.getText().equals(productName)) {
                        wait.until(ExpectedConditions.elementToBeClickable(product)).click();
                        return;
                    }
                }
                break;
            } catch (StaleElementReferenceException | InterruptedException e) {
                if (attempts == 2) throw new RuntimeException("Failed to click product after 3 attempts", e);
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }

    public void addToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
    }

    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }

    public boolean isProductListDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(productLinks));
            return !productLinks.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
} 