Feature: Manage Contacts
  As a user, I want to add, edit, and delete a contact
  so that I can manage my contact list effectively.

  @ManageContacts @MCT1
  Scenario: Add a new contact
    Given the user is on the login screen of the Cogmento app
    When the user enters valid credentials and taps the login button
      | siva8kolli@gmail.com | Selenium@123 |
    And the user taps the hamburger menu
    Given the user is on the "Contacts" page
    When the user clicks on "CONTACTS"
    And enters the contact details
      | firstName | lastName | company    | identity   |
      | Siva      | Kolli    | Automation | Brown Eyes |
    And clicks on "Save"
    Then the contact should be added successfully

  @ManageContacts @MCT2
  Scenario: Edit an existing contact
    Given the contact "Siva Kolli" exists in the contact list
    When the user selects "Edit" for "Siva Kolli"
    And updates the contact details
      | email            |
      | siva@yopmail.com |
    Then the contact details should be updated successfully

  @ManageContacts @MCT3
  Scenario: Delete a contact
    Given the contact "Siva Kolli" exists in the contact list
    When the user selects "Delete" for "Siva Kolli"
    And confirms the deletion
    Then the contact should be removed from the contact list
