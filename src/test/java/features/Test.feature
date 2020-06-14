Feature: Test task

  Scenario: Test the 'View All' button on the main page
    Given the user is on the main page
    And a 100 results are displayed
    When the user clicks on View All
    Then more then 100 results are displayed

  Scenario: Test the Watchlist
    Given the user is on the main page
    When the user adds 7 items to the Watchlist
    And the user clicks on Watchlist
    Then all the currencies are in the Watchlist

  Scenario: Test the filters
    Given the user is on the main page
    When they open the full list
    And record 3 top currencies
    And apply the price filter
    Then top 3 currencies are different