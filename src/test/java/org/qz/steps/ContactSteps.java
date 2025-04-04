package org.qz.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
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

public class ContactSteps extends WebDriverFactory {

    public LoginPage loginPage;
    public HomePage homePage;
    public AddContactPage addContactPage;
    public ContactsPage contactsPage;
    private Scenario scenario;

    public ContactSteps() {
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
        scenario.attach(screenshot, "image/png", screenshot.toString());
    }

    @Given("the user is on the login screen of the Cogmento app")
    public void the_user_is_on_the_login_screen_of_the_cogmento_app() {
        Assert.assertTrue(loginPage.isLoginPageDisplayed());
    }

    @When("the user enters valid credentials and taps the login button")
    public void the_user_enters_valid_credentials_and_taps_the_login_button(DataTable dataTable) {
        List<String> credentials = dataTable.row(0);
        loginPage.login(credentials.get(0), credentials.get(1));
    }

    @When("the user taps the hamburger menu")
    public void the_user_taps_the_hamburger_menu() {
        homePage.openHamburgerMenu();
    }

    @Then("the user should be navigated to the {string} section")
    public void the_user_should_be_navigated_to_the_section(String menu) {
        homePage.selectMenuItem(menu);
    }

    @Then("the user taps the plus icon to add a new contact")
    public void the_user_taps_the_plus_icon_to_add_a_new_contact() {
        homePage.tapOnPlusIcon();
    }

    @Then("the user enters the contact details")
    public void the_user_enters_the_contact_details(DataTable dataTable) {
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

    @Then("the user verifies that the contact is created")
    public void theUserVerifyContactIsCreated() {
        Assert.assertTrue(contactsPage.isContactCreated("Ravi", "Sindri"));
        attachScreenshot(scenario);
    }

    @And("the user opens the contact")
    public void theUserOpensTheContact() {
        contactsPage.openContact("Ravi", "Sindri");
    }

    @And("the user deletes the contact")
    public void theUserDeletesTheContact() {
        contactsPage.deleteContact();
    }

    @Then("the user verifies that the contact is deleted")
    public void theUserVerifiesThatTheContactIsDeleted() {
        Assert.assertTrue(contactsPage.isMessageFound());
        attachScreenshot(scenario);
    }

    @And("the user edit the contact by adding email")
    public void theUserEditTheContactByAddingEmail(DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        String email = null;
        for (Map<String, String> dataMap : data) {
            email = dataMap.get("email");
        }
        contactsPage.editContact(email);

    }

    @Then("the user verifies that the contact is updated with email {string}")
    public void theUserVerifiesThatTheContactIsUpdatedWithEmail(String email) {
        contactsPage.isContactUpdated(email);
        attachScreenshot(scenario);
    }
}
