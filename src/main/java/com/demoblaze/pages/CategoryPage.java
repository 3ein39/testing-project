package com.demoblaze.pages;

import com.demoblaze.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.StaleElementReferenceException;
import java.util.List;
import java.util.ArrayList;

public class CategoryPage extends BasePage {

    @FindBy(linkText = "Phones")
    private WebElement phonesCategory;

    @FindBy(linkText = "Laptops")
    private WebElement laptopsCategory;

    @FindBy(linkText = "Monitors")
    private WebElement monitorsCategory;

    @FindBy(css = ".card-title a")
    private List<WebElement> categoryProducts;

    public CategoryPage(WebDriver driver) {
        super(driver);
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

    public int getProductCount() {
        try {
            Thread.sleep(1000); // Wait for products to update
            wait.until(ExpectedConditions.visibilityOfAllElements(categoryProducts));
            return categoryProducts.size();
        } catch (InterruptedException e) {
            return 0;
        }
    }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        try {
            Thread.sleep(1000); // Wait for products to update
            wait.until(ExpectedConditions.visibilityOfAllElements(categoryProducts));
            for (int attempts = 0; attempts < 3; attempts++) {
                try {
                    names = categoryProducts.stream()
                            .map(WebElement::getText)
                            .toList();
                    break;
                } catch (StaleElementReferenceException e) {
                    if (attempts == 2) throw e;
                }
            }
        } catch (InterruptedException e) {
            // Handle exception
        }
        return names;
    }

    public boolean isCategoryPageLoaded(String category) {
        try {
            Thread.sleep(1000); // Wait for products to update
            List<String> products = getProductNames();
            switch (category.toLowerCase()) {
                case "phone":
                    return products.stream().anyMatch(name -> name.toLowerCase().contains("samsung") || 
                                                            name.toLowerCase().contains("nokia") || 
                                                            name.toLowerCase().contains("iphone"));
                case "laptop":
                    return products.stream().anyMatch(name -> name.toLowerCase().contains("notebook") || 
                                                            name.toLowerCase().contains("macbook") || 
                                                            name.toLowerCase().contains("laptop"));
                case "monitor":
                    return products.stream().anyMatch(name -> name.toLowerCase().contains("monitor") || 
                                                            name.toLowerCase().contains("asus") || 
                                                            name.toLowerCase().contains("apple"));
                default:
                    return false;
            }
        } catch (InterruptedException e) {
            return false;
        }
    }
} 