package org.qz.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertFalse;

public class ApiSteps {

    private Response response;
    private RequestSpecification request;

    @Given("the API base URI is {string}")
    public void theApiBaseURIIs(String baseUri) {
        RestAssured.baseURI = baseUri;
        request = given().header("Content-Type", "application/json");
    }

    @When("I send a GET request to {string}")
    public void iSendAGetRequestTo(String endpoint) {
        response = request.when().get(endpoint);
    }

    @When("I send a POST request to {string} with the following body:")
    public void iSendAPostRequestToWithBody(String endpoint, DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        Map<String, String> body = rows.get(0);

        String jsonBody = String.format(
            "{\"title\":\"%s\",\"body\":\"%s\",\"userId\":%s}",
            body.get("title"), body.get("body"), body.get("userId")
        );

        response = request.body(jsonBody).when().post(endpoint);
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        assertThat("Unexpected status code", response.statusCode(), equalTo(expectedStatusCode));
    }

    @And("the response body field {string} should equal {int}")
    public void theResponseBodyFieldShouldEqualInt(String field, int expectedValue) {
        assertThat(response.jsonPath().getInt(field), equalTo(expectedValue));
    }

    @And("the response body field {string} should equal {string}")
    public void theResponseBodyFieldShouldEqualString(String field, String expectedValue) {
        assertThat(response.jsonPath().getString(field), equalTo(expectedValue));
    }

    @And("the response body string field {string} should not be empty")
    public void theResponseBodyStringFieldShouldNotBeEmpty(String field) {
        String value = response.jsonPath().getString(field);
        assertFalse(value == null || value.isEmpty(),
            "Expected field '" + field + "' to not be empty, but it was.");
    }

    @And("the response should contain more than {int} items")
    public void theResponseShouldContainMoreThanItems(int minCount) {
        List<?> items = response.jsonPath().getList("$");
        assertThat("Response list size", items.size(), greaterThan(minCount));
    }
}
