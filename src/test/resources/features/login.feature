Feature: Login

  @LT1 @Smoke
  Scenario: Verify login success with valid credentials
    Given User open application
    When User enter valid credentials
    And User click on submit button
    Then User verify products page

  @LT2 @Smoke
  Scenario: Verify login success with valid credentials using SQLite key
    Given User open application
    When User enter valid credentials using key "default_login"
    And User click on submit button
    Then User verify products page


  @LT3 @Smoke
  Scenario Outline: Verify login success with valid credentials using SQLite key examples
    Given User open application
    When User enter valid credentials using key "<credentialsKey>"
    And User click on submit button
    Then User verify products page
    Examples:
      | credentialsKey |
      | default_login  |
      | default_login  |
      | default_login  |
      | default_login  |