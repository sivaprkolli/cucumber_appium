package org.qz.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.qz.automation.WebDriverFactory;
import org.qz.pages.LoginPage;
import org.qz.utils.SQLiteTestDataStore;

import java.util.List;
import java.util.Map;

public class LoginSteps extends WebDriverFactory {
    private static final String DEFAULT_LOGIN_KEY = System.getProperty("testdata.login.key", "default_login");

    public LoginPage loginPage;

    @Given("User open application")
    public void userOpenApplication() {
        loginPage = new LoginPage(driver);
    }

    @When("User enter valid credentials")
    public void userEnterValidCredentials() {
                Map<String, String> credentials = SQLiteTestDataStore.getCredentials(DEFAULT_LOGIN_KEY);
                loginPage.login(credentials.get("username"), credentials.get("password"));
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
          loginPage.login(userName, password);
    }

    @When("User enter valid credentials using table")
    public void userEnterValidCredentialsUsingTable(DataTable dataTable) {

        List<String> data = dataTable.row(0);

        System.out.println(data.get(0));
        System.out.println(data.get(1));

        loginPage.login(data.get(0), data.get(1));
    }

    @When("User enter valid credentials using key {string}")
    public void userEnterValidCredentialsUsingKey(String credentialsKey) {
        Map<String, String> credentials = SQLiteTestDataStore.getCredentials(credentialsKey);
        loginPage.login(credentials.get("username"), credentials.get("password"));
    }
}
