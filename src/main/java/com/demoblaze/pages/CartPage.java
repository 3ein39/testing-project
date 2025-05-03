package com.demoblaze.pages;

import com.demoblaze.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.Alert;

public class CartPage extends BasePage {

    @FindBy(css = ".success")
    private List<WebElement> cartItems;

    @FindBy(xpath = "//button[contains(text(),'Place Order')]")
    private WebElement placeOrderButton;

    @FindBy(css = ".btn-primary")
    private WebElement purchaseButton;

    @FindBy(css = ".sweet-alert h2")
    private WebElement orderConfirmation;

    @FindBy(id = "name")
    private WebElement nameField;

    @FindBy(id = "country")
    private WebElement countryField;

    @FindBy(id = "city")
    private WebElement cityField;

    @FindBy(id = "card")
    private WebElement cardField;

    @FindBy(id = "month")
    private WebElement monthField;

    @FindBy(id = "year")
    private WebElement yearField;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getCartItemCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfAllElements(cartItems));
            return cartItems.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void clickPlaceOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click();
    }

    public void fillPurchaseForm(String name, String country, String city, String card, String month, String year) {
        wait.until(ExpectedConditions.visibilityOf(nameField)).sendKeys(name);
        wait.until(ExpectedConditions.visibilityOf(countryField)).sendKeys(country);
        wait.until(ExpectedConditions.visibilityOf(cityField)).sendKeys(city);
        wait.until(ExpectedConditions.visibilityOf(cardField)).sendKeys(card);
        wait.until(ExpectedConditions.visibilityOf(monthField)).sendKeys(month);
        wait.until(ExpectedConditions.visibilityOf(yearField)).sendKeys(year);
    }

    public void clickPurchase() {
        try {
            System.out.println("Clicking purchase button...");
            Thread.sleep(2000); // Wait for modal to be ready
            
            // Wait for the purchase button to be present and visible
            By purchaseButtonLocator = By.xpath("//button[contains(text(), 'Purchase')]");
            WebElement purchaseBtn = wait.until(ExpectedConditions.presenceOfElementLocated(purchaseButtonLocator));
            wait.until(ExpectedConditions.elementToBeClickable(purchaseBtn));
            
            // Scroll the button into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", purchaseBtn);
            Thread.sleep(1000); // Wait for scroll
            
            // Click using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", purchaseBtn);
            System.out.println("Purchase button clicked successfully");
            
            // Wait for any alert that might appear
            try {
                Thread.sleep(2000);
                Alert alert = driver.switchTo().alert();
                System.out.println("Alert found: " + alert.getText());
                alert.accept();
            } catch (Exception e) {
                System.out.println("No alert found after purchase click");
            }
        } catch (Exception e) {
            System.out.println("Error clicking purchase button: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getOrderConfirmation() {
        try {
            System.out.println("Waiting for order confirmation...");
            
            // First check if we're still on the cart page
            String currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL: " + currentUrl);
            
            // Wait for the confirmation dialog with a longer timeout
            WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(20));
            
            // Try different selectors for the confirmation message
            try {
                // Wait for the success message
                By successMessageLocator = By.xpath("//h2[contains(text(), 'Thank you')]");
                WebElement confirmationElement = extendedWait.until(
                    ExpectedConditions.visibilityOfElementLocated(successMessageLocator)
                );
                String confirmationText = confirmationElement.getText();
                System.out.println("Order confirmation text: " + confirmationText);
                return confirmationText;
            } catch (Exception e) {
                System.out.println("Could not find confirmation message: " + e.getMessage());
            }
            
            return "";
        } catch (Exception e) {
            System.out.println("Error getting order confirmation: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    public boolean isCartEmpty() {
        return getCartItemCount() == 0;
    }
} 