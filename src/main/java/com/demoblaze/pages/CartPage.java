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
            waitHelper.waitForAllElementsVisible(cartItems);
            return cartItems.size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void clickPlaceOrder() {
        waitHelper.waitForElementToBeClickable(placeOrderButton).click();
    }

    public void fillPurchaseForm(String name, String country, String city, String card, String month, String year) {
        waitHelper.waitForElementVisible(nameField).sendKeys(name);
        waitHelper.waitForElementVisible(countryField).sendKeys(country);
        waitHelper.waitForElementVisible(cityField).sendKeys(city);
        waitHelper.waitForElementVisible(cardField).sendKeys(card);
        waitHelper.waitForElementVisible(monthField).sendKeys(month);
        waitHelper.waitForElementVisible(yearField).sendKeys(year);
    }

    public void clickPurchase() {
        try {
            System.out.println("Clicking purchase button...");
            
            // Wait for the purchase button to be present and visible
            By purchaseButtonLocator = By.xpath("//button[contains(text(), 'Purchase')]");
            WebElement purchaseBtn = waitHelper.waitForElementVisible(purchaseButtonLocator);
            waitHelper.waitForElementToBeClickable(purchaseBtn);
            
            // Scroll the button into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", purchaseBtn);
            
            // Wait for scrolling to complete (replacing Thread.sleep)
            waitHelper.waitWithTimeout(ExpectedConditions.elementToBeClickable(purchaseBtn), 3);
            
            // Click using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", purchaseBtn);
            System.out.println("Purchase button clicked successfully");
            
            // Check for any alert that might appear
            alertHelper.acceptAlertIfPresent();
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
            
            // Try different selectors for the confirmation message
            try {
                // Wait for the success message with extended timeout
                By successMessageLocator = By.xpath("//h2[contains(text(), 'Thank you')]");
                WebElement confirmationElement = waitHelper.waitWithTimeout(
                    ExpectedConditions.visibilityOfElementLocated(successMessageLocator), 
                    20
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

    public String getCompleteOrderConfirmation() {
        try {
            System.out.println("Getting complete order confirmation...");
            
            // Wait for the confirmation dialog to be visible
            By confirmationDialogLocator = By.cssSelector(".sweet-alert");
            WebElement confirmationDialog = waitHelper.waitWithTimeout(
                ExpectedConditions.visibilityOfElementLocated(confirmationDialogLocator), 
                20
            );
            
            // Get all text elements in the confirmation dialog
            List<WebElement> confirmationElements = confirmationDialog.findElements(By.tagName("p"));
            StringBuilder confirmationText = new StringBuilder();
            
            // Get the header first
            WebElement headerElement = confirmationDialog.findElement(By.tagName("h2"));
            confirmationText.append(headerElement.getText()).append("\n");
            
            // Append all paragraph texts
            for (WebElement element : confirmationElements) {
                confirmationText.append(element.getText()).append("\n");
            }
            
            String result = confirmationText.toString();
            System.out.println("Complete confirmation text: " + result);
            return result;
        } catch (Exception e) {
            System.out.println("Error getting complete order confirmation: " + e.getMessage());
            e.printStackTrace();
            return "";
        }
    }

    public boolean isCartEmpty() {
        return getCartItemCount() == 0;
    }

    public WebElement getNameField() {
        return nameField;
    }

    public WebElement getCardField() {
        return cardField;
    }

    public WebElement getCountryField() {
        return countryField;
    }

    public WebElement getCityField() {
        return cityField;
    }

    public WebElement getMonthField() {
        return monthField;
    }

    public WebElement getYearField() {
        return yearField;
    }
}