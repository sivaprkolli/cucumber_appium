package org.qz.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.qz.automation.WebDriverFactory;
import org.qz.pages.LoginPage;
import org.testng.Assert;

import java.util.List;

public class LoginSteps extends WebDriverFactory {
    public LoginPage loginPage;

    @Given("User open application")
    public void userOpenApplication() {
        loginPage = new LoginPage(driver);
    }

    @When("User enter valid credentials")
    public void userEnterValidCredentials() {
      //  loginPage.enterCredentials("standard_user", "secret_sauce");
    }

    @And("User click on submit button")
    public void userClickOnSubmitButton() {
       // loginPage.clickOnLoginButton();
    }

    @Then("User verify products page")
    public void userVerifyProductsPage() {
//        Assert.assertTrue(loginPage.getProductHeading().equals("Products"));
//        loginPage.navigateBack();
    }

    @When("User enter valid credentials {string} and {string}")
    public void userEnterValidCredentialsAnd(String userName, String password) {
       // loginPage.enterCredentials(userName, password);
    }

    @When("User enter valid credentials using table")
    public void userEnterValidCredentialsUsingTable(DataTable dataTable) {

        List<String> data = dataTable.row(0);

        System.out.println(data.get(0));
        System.out.println(data.get(1));

       // loginPage.enterCredentials(data.get(0), data.get(1));
    }
}
