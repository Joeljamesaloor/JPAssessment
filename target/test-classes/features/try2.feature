Feature: Verify accuracy of news article on The Guardian website

  Scenario: Verify accuracy of news article
    Given I am on the The Guardian website
    When I click on the news article with title "Example News Article"
    Then I should see supporting evidence to verify its accuracy
