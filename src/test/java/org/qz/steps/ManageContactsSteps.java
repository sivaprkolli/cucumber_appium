package org.qz.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.qz.automation.WebDriverFactory;
import org.qz.pages.AddContactPage;
import org.qz.pages.ContactsPage;
import org.qz.pages.HomePage;
import org.qz.pages.LoginPage;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class ManageContactsSteps extends WebDriverFactory {

    public LoginPage loginPage;
    public HomePage homePage;
    public AddContactPage addContactPage;
    public ContactsPage contactsPage;
    private Scenario scenario;

    public ManageContactsSteps() {
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        addContactPage = new AddContactPage(driver);
        contactsPage = new ContactsPage(driver);
    }

    @io.cucumber.java.Before
    public void beforeScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public void attachScreenshot(Scenario scenario) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", scenario.getName());
    }

    @Given("the user is on the {string} page")
    public void the_user_is_on_the_page(String string) {
        homePage.openHamburgerMenu();
    }
    @When("the user clicks on {string}")
    public void the_user_clicks_on(String menu) {
        homePage.selectMenuItem(menu);
        homePage.tapOnPlusIcon();
    }
    @When("enters the contact details")
    public void enters_the_contact_details(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        String firstName = null;
        String lastName = null;
        String company = null;
        String identity = null;
        for (Map<String, String> dataMap : data) {
            firstName = dataMap.get("firstName");
            lastName = dataMap.get("lastName");
            company = dataMap.get("company");
            identity = dataMap.get("identity");

        }
        addContactPage.addContact(firstName, lastName, company, identity);
    }
    @When("clicks on {string}")
    public void clicks_on(String string) {
        addContactPage.clickOnDone();
    }
    @Then("the contact should be added successfully")
    public void the_contact_should_be_added_successfully() {
        Assert.assertTrue(contactsPage.isContactCreated("Ravi", "Sindri"));
        attachScreenshot(scenario);
    }

    @Given("the contact {string} exists in the contact list")
    public void the_contact_exists_in_the_contact_list(String string) {
        Assert.assertTrue(contactsPage.isContactCreated("Ravi", "Sindri"));
    }
    @When("the user selects {string} for {string}")
    public void the_user_selects_for(String string, String string2) {
        contactsPage.openContact("Ravi", "Sindri");
    }
    @When("updates the contact details")
    public void updates_the_contact_details(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        String email = null;
        for (Map<String, String> dataMap : data) {
            email = dataMap.get("email");
        }
        contactsPage.editContact(email);

    }
    @Then("the contact details should be updated successfully")
    public void the_contact_details_should_be_updated_successfully() {
        contactsPage.isContactUpdated("ravi@yopmail.com");
        attachScreenshot(scenario);
    }

    @When("confirms the deletion")
    public void confirms_the_deletion() {
        contactsPage.deleteContact();
    }
    @Then("the contact should be removed from the contact list")
    public void the_contact_should_be_removed_from_the_contact_list() {
        Assert.assertTrue(contactsPage.isMessageFound());
        attachScreenshot(scenario);
    }
}
