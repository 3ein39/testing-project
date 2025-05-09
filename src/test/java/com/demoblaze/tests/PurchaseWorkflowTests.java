package com.demoblaze.tests;

import org.testng.Reporter;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.*;


public class PurchaseWorkflowTests extends BaseTest {

    @Test(description = "Verify complete purchase process")
    public void testCompletePurchase() {
        Reporter.log("Starting testCompletePurchase...", true);
        
        addProductToCart("Samsung galaxy s6");
        
        productPage.goToCart();
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
        
        assertTrue(confirmation.contains("Thank you for your purchase"), 
            "Order confirmation message should be displayed. Actual message: " + confirmation);
    }

    @Test(description = "Verify purchase form validation with invalid data")
    public void testInvalidPurchaseFormData() {
        Reporter.log("Starting testInvalidPurchaseFormData...", true);
        
        addProductToCart("Samsung galaxy s6");
        
        productPage.goToCart();
        cartPage.clickPlaceOrder();
        
        cartPage.fillPurchaseForm(
            "Test User",
            "Test Country",
            "Test City",
            "Invalid-Card-Number",
            "ABC",
            "XYZ"
        );
        
        cartPage.clickPurchase();
        
        String confirmation = cartPage.getOrderConfirmation();
        Reporter.log("Confirmation text: " + confirmation, true);
        
        assertFalse(confirmation.contains("Thank you for your purchase"),
            "Order confirmation should appear despite invalid numeric data, highlighting the validation issue");
    }

    @Test(description = "Verify purchase form validation with partial data")
    public void testPartialPurchaseFormValidation() {
        Reporter.log("Starting testPartialPurchaseFormValidation...", true);
        
        addProductToCart("Samsung galaxy s6");
        
        productPage.goToCart();
        cartPage.clickPlaceOrder();
        
        waitHelper.waitForElementVisible(cartPage.getNameField()).sendKeys("Test User");
        waitHelper.waitForElementVisible(cartPage.getCardField()).sendKeys("4111111111111111");
        
        cartPage.clickPurchase();
        
        String confirmation = cartPage.getOrderConfirmation();
        Reporter.log("Confirmation text: " + confirmation, true);
        
        assertFalse(confirmation.contains("Thank you for your purchase"),
            "Order confirmation should not appear with incomplete form data, highlighting the validation issue");
    }

    @Test(description = "Verify purchase date in confirmation is correct")
    public void testPurchaseDateIsCorrect() {
        Reporter.log("Starting testPurchaseIsCorrect...", true);
        
        addProductToCart("Samsung galaxy s6");
        
        productPage.goToCart();
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
        
        String confirmationText = cartPage.getCompleteOrderConfirmation();
        Reporter.log("Full confirmation text: " + confirmationText, true);
        
        Calendar currentDate = Calendar.getInstance();
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
        int currentYear = currentDate.get(Calendar.YEAR);
        
        Reporter.log("Current date: " + currentDay + "/" + currentMonth + "/" + currentYear, true);
        
        Pattern datePattern = Pattern.compile("Date:\\s*(\\d+)/(\\d+)/(\\d+)");
        Matcher matcher = datePattern.matcher(confirmationText);
        
        if (matcher.find()) {
            int displayedMonth = Integer.parseInt(matcher.group(2));
            int displayedDay = Integer.parseInt(matcher.group(1));
            int displayedYear = Integer.parseInt(matcher.group(3));
            
            Reporter.log("Displayed date: " + displayedDay + "/" + displayedMonth + "/" + displayedYear, true);
            
            assertEquals(displayedMonth, currentMonth, "Displayed month should be equal to current month");
            assertEquals(displayedDay, currentDay, "Displayed day should be equal to current day");
            assertEquals(displayedYear, currentYear, "Displayed year should be equal to current year");
        } else {
            fail("Could not find date in confirmation text: " + confirmationText);
        }
    }
}