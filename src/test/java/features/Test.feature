Feature: Login Feature
  Verify if user is able to Login in to the site

  Scenario: Login as a authenticated user
    Given user is on the main page
    And a 100 results are displayed
    When user clicks on View All
    Then more then 100 results are displayed