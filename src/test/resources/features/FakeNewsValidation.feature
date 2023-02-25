Feature: Verify the Validity of the First News Article on The Guardian website

  Scenario Outline: Verify the Validity of the First News Article on The Guardian website
    Given I am on "<news-website>"
    When I read the first news article
    Then I should be able to find its title
    And I should search for similar information on "<Bing-SearchEngine>"
    And if I find two or more articles with similar information
    Then I should consider the first Guardian news article as not Fake


    Examples:
      | news-website                               | Bing-SearchEngine               |
      | https://www.theguardian.com/tone/news      | https://www.bing.com/search?q=  |
