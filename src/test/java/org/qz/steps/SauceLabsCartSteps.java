package org.qz.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.qz.automation.WebDriverFactory;
import org.qz.pages.SauceLabsShopPage;
import org.qz.utils.SQLiteTestDataStore;
import org.testng.Assert;

import java.util.Map;

public class SauceLabsCartSteps extends WebDriverFactory {

    private SauceLabsShopPage sauceLabsShopPage;
    private Map<String, String> activeCheckoutData;
    private String activeCheckoutKey;

    @Given("the user launches Sauce Labs app on {string}")
    public void theUserLaunchesSauceLabsAppOn(String platform) {
        sauceLabsShopPage = new SauceLabsShopPage(driver);

        Assert.assertTrue(sauceLabsShopPage.isLoginPageDisplayed(), "Sauce Labs login page is not displayed");
    }

    @When("the user logs in to Sauce Labs app with {string} and {string}")
    public void theUserLogsInToSauceLabsAppWithAnd(String username, String password) {
        sauceLabsShopPage.login(username, password);
        Assert.assertTrue(sauceLabsShopPage.isProductsPageDisplayed(), "Products page is not displayed after login");
    }

    @When("the user logs in to Sauce Labs app using credentials key {string}")
    public void theUserLogsInToSauceLabsAppUsingCredentialsKey(String credentialsKey) {
        Map<String, String> credentials = SQLiteTestDataStore.getCredentials(credentialsKey);
        sauceLabsShopPage.login(credentials.get("username"), credentials.get("password"));
        Assert.assertTrue(sauceLabsShopPage.isProductsPageDisplayed(), "Products page is not displayed after login");
    }

    @And("the user adds {string} product to the cart")
    public void theUserAddsProductToTheCart(String productName) {
        sauceLabsShopPage.addProductToCart(productName);
        sauceLabsShopPage.openCart();
    }

    @Then("the user should see {string} in the cart")
    public void theUserShouldSeeInTheCart(String productName) {
        sauceLabsShopPage.openCart();
        Assert.assertTrue(sauceLabsShopPage.isProductDisplayedInCart(productName), "Expected product was not found in cart");
    }

    @And("the user taps on checkout")
    public void theUserTapsOnCheckout() {
        sauceLabsShopPage.tapCheckout();
    }

    @And("the user adds checkout details {string}, {string}, and {string}")
    public void theUserAddsCheckoutDetailsAnd(String firstName, String lastName, String pin) {
        sauceLabsShopPage.enterCheckoutInformation(firstName, lastName, pin);
    }

    @And("the user adds checkout details using key {string}")
    public void theUserAddsCheckoutDetailsUsingKey(String checkoutKey) {
        activeCheckoutData = SQLiteTestDataStore.getCheckoutData(checkoutKey);
        activeCheckoutKey = checkoutKey;
        sauceLabsShopPage.enterCheckoutInformation(
            activeCheckoutData.get("firstName"),
            activeCheckoutData.get("lastName"),
            activeCheckoutData.get("pin")
        );
    }

    @And("the user taps continue")
    public void theUserTapsContinue() {
        sauceLabsShopPage.tapContinueOnCheckout();
    }

    @And("the user taps finish")
    public void theUserTapsFinish() {
        sauceLabsShopPage.tapFinish();
    }

    @Then("the user should see order confirmation message {string}")
    public void theUserShouldSeeOrderConfirmationMessage(String message) {
        Assert.assertTrue(sauceLabsShopPage.isOrderConfirmationDisplayed(message), "Order confirmation message is not displayed");
    }

    @Then("the user should see order confirmation message for checkout key {string}")
    public void theUserShouldSeeOrderConfirmationMessageForCheckoutKey(String checkoutKey) {
        if (activeCheckoutData == null || !checkoutKey.equals(activeCheckoutKey)) {
            activeCheckoutData = SQLiteTestDataStore.getCheckoutData(checkoutKey);
            activeCheckoutKey = checkoutKey;
        }
        Assert.assertTrue(
            sauceLabsShopPage.isOrderConfirmationDisplayed(activeCheckoutData.get("orderMessage")),
            "Order confirmation message is not displayed"
        );
    }
}
