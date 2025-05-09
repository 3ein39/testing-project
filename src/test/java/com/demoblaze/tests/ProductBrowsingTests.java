package com.demoblaze.tests;

import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;


public class ProductBrowsingTests extends BaseTest {

    @Test(description = "Verify home page elements are displayed correctly")
    public void testHomePageNavigation() {
        assertTrue(homePage.isLoginLinkDisplayed(), "Login link should be displayed");
        assertTrue(homePage.isSignUpLinkDisplayed(), "Sign up link should be displayed");
        assertTrue(homePage.isCartLinkDisplayed(), "Cart link should be displayed");
        assertTrue(homePage.isContactLinkDisplayed(), "Contact link should be displayed");

        assertTrue(homePage.isPhonesCategoryDisplayed(), "Phones category should be displayed");
        assertTrue(homePage.isLaptopsCategoryDisplayed(), "Laptops category should be displayed");
        assertTrue(homePage.isMonitorsCategoryDisplayed(), "Monitors category should be displayed");

        assertTrue(homePage.isProductListDisplayed(), "Product list should be displayed");
    }

    @Test(description = "Verify clicking on logo returns to home page")
    public void testLogoNavigation() {
        homePage.clickLogo();

        assertTrue(homePage.isPhonesCategoryDisplayed(), "Phones category should be displayed");
        assertTrue(homePage.isLaptopsCategoryDisplayed(), "Laptops category should be displayed");
        assertTrue(homePage.isMonitorsCategoryDisplayed(), "Monitors category should be displayed");

        String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("index.html"), "URL should end with 'index.html'");

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("index.html"), "URL should contain 'index.html'");
    }
    
    @Test(description = "Verify navigation between product categories")
    public void testCategoryNavigation() {
        categoryPage.clickPhonesCategory();
        assertTrue(categoryPage.isCategoryPageLoaded("phone"), "Should be on phones category");
        
        categoryPage.clickLaptopsCategory();
        assertTrue(categoryPage.isCategoryPageLoaded("laptop"), "Should be on laptops category");
        
        categoryPage.clickMonitorsCategory();
        assertTrue(categoryPage.isCategoryPageLoaded("monitor"), "Should be on monitors category");
    }
    
    @Test(description = "Verify products are displayed in Phones category")
    public void testPhonesCategory() {
        categoryPage.clickPhonesCategory();
        assertTrue(categoryPage.isCategoryPageLoaded("phone"), "Phones category page should be loaded");
        assertTrue(categoryPage.getProductCount() > 0, "Phones category should have products");
        List<String> phoneProducts = categoryPage.getProductNames();
        assertTrue(phoneProducts.stream().anyMatch(name -> name.contains("Samsung")), 
            "Phones category should contain Samsung products");
    }

    @Test(description = "Verify products are displayed in Laptops category")
    public void testLaptopsCategory() {
        categoryPage.clickLaptopsCategory();
        assertTrue(categoryPage.isCategoryPageLoaded("laptop"), "Laptops category page should be loaded");
        assertTrue(categoryPage.getProductCount() > 0, "Laptops category should have products");
        List<String> laptopProducts = categoryPage.getProductNames();
        assertTrue(laptopProducts.stream().anyMatch(name -> name.contains("MacBook")), 
            "Laptops category should contain MacBook products");
    }
    
    @DataProvider(name = "categoryTestData")
    public Object[][] getCategoryData() {
        return new Object[][] {
            {"phone", "Samsung"},
            {"laptop", "MacBook"},
            {"monitor", "Apple"}
        };
    }

    @Test(dataProvider = "categoryTestData", description = "Verify products in all categories")
    public void testProductCategory(String category, String expectedBrand) {
        Reporter.log("Testing " + category + " category, expecting " + expectedBrand + " products", true);

        switch (category.toLowerCase()) {
            case "phone":
                categoryPage.clickPhonesCategory();
                break;
            case "laptop":
                categoryPage.clickLaptopsCategory();
                break;
            case "monitor":
                categoryPage.clickMonitorsCategory();
                break;
        }

        assertTrue(categoryPage.isCategoryPageLoaded(category),
                category + " category page should be loaded");

        assertTrue(categoryPage.getProductCount() > 0,
                category + " category should have products");

        List<String> products = categoryPage.getProductNames();
        Reporter.log("Found products: " + products, true);

        assertTrue(products.stream().anyMatch(name -> name.contains(expectedBrand)),
                category + " category should contain " + expectedBrand + " products");
    }
}