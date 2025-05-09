package com.demoblaze.tests;

import org.testng.Reporter;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


public class ShoppingCartTests extends BaseTest {

    @Test(description = "Verify product selection and addition to cart")
    public void testProductSelection() {
        assertTrue(productPage.isProductListDisplayed(), "Product list should be displayed");
        
        String productName = "Samsung galaxy s6";
        addProductToCart(productName);
        
        productPage.goToCart();
        
        waitHelper.waitForPageLoad();
        
        assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one item");
    }

    @Test(description = "Verify adding product to cart functionality")
    public void testCartFunctionality() {
        addProductToCart("Samsung galaxy s6");
        
        productPage.goToCart();
        
        waitHelper.waitForPageLoad();
        
        assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one item");
    }

    @Test(description = "Verify adding product from specific category to cart")
    public void testProductFromCategory() {
        categoryPage.clickPhonesCategory();
        waitHelper.waitForPageLoad();
        
        addProductToCart("Samsung galaxy s6");
        
        productPage.goToCart();
        
        waitHelper.waitForPageLoad();
  
        assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one item");
    }
    
    @Test(description = "Verify purchase attempt with empty cart")
    public void testEmptyCartPurchase() {
        Reporter.log("Starting testEmptyCartPurchase...", true);
        
        homePage.clickCartLink();
        
        assertTrue(cartPage.isCartEmpty(), "Cart should be empty for this test");
        
        cartPage.clickPlaceOrder();
        
        cartPage.fillPurchaseForm(
            "Test User",
            "Test Country",
            "Test City",
            "4111111111111111",
            "12",
            "2025"
        );
        
        cartPage.clickPurchase();
        
        String confirmation = cartPage.getOrderConfirmation();
        Reporter.log("Confirmation text: " + confirmation, true);
        
        assertFalse(confirmation.contains("Thank you for your purchase"),
            "Order confirmation should not appear with empty cart, highlighting the validation issue");
    }
}