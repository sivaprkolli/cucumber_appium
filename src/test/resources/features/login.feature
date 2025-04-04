Feature: Login

  @LT1 @Smoke
  Scenario: Verify login success with valid credentials
    Given User open application
    When User enter valid credentials
    And User click on submit button
    Then User verify products page

  @LT2 @Smoke
  Scenario: Verify login success with valid credentials using table
    Given User open application
   # When User enter valid credentials "standard_user" and "secret_sauce"
    When User enter valid credentials using table
      | standard_user | secret_sauce |
    And User click on submit button
    Then User verify products page


  @LT3 @Smoke
  Scenario Outline: Verify login success with valid credentials using examples
    Given User open application
    When User enter valid credentials "<username>" and "<password>"
    And User click on submit button
    Then User verify products page
    Examples:
      | username      | password     |
      | standard_user | secret_sauce |
      | visual_user | secret_sauce |
      | performance_glitch_user | secret_sauce |
      | problem_user | secret_sauce |