Feature: Verify first news article from The Guardian website
  Scenario: Verify first news article is available in Google search results
    Given I am on The Guardian website
    # When I retrieve the first news article
    And search for title in Google


