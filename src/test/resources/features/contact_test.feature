Feature: Contact

  @CT1
  Scenario: Add a new contact successfully
    Given the user is on the login screen of the Cogmento app
    When the user enters valid credentials and taps the login button
      | siva8kolli@gmail.com | Selenium@123 |
    And the user taps the hamburger menu
    Then the user should be navigated to the "CONTACTS" section
    And the user taps the plus icon to add a new contact
    And the user enters the contact details
      | firstName | lastName | company   | identity   |
      | Ravi      | Sindri   | QualiZeal | Brown Eyes |
    Then the user verifies that the contact is created
    And the user opens the contact
    And the user edit the contact by adding email
      | email            |
      | ravi@yopmail.com |
    Then the user verifies that the contact is updated with email "ravi@yopmail.com"
    And the user opens the contact
    And the user deletes the contact
    Then the user verifies that the contact is deleted
