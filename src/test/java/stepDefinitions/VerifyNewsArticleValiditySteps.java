package stepDefinitions;

import config.CustomDriver;
import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import objects.NewsArticle;
import pages.HomePage;

import java.io.IOException;

public class VerifyNewsArticleValiditySteps {

    private final WebDriver driver;
    private final HomePage homePage;
    private NewsArticle newsArticle;

    public VerifyNewsArticleValiditySteps() throws IOException {
        this.driver = CustomDriver.getDriver();
        this.homePage = new HomePage(driver);
        this.newsArticle = new NewsArticle(driver);
    }


    @Given("I am on {string}")
    public void i_am_on(String newsWebsite) {
        //HomePage homePage = new HomePage(driver);
        homePage.navigateToNewsSection(newsWebsite);
    }

    @When("I read the first news article")
    public void i_read_the_first_news_article() {
        newsArticle = new NewsArticle(driver);
        newsArticle.readFirstArticle();
    }

    @Then("I should be able to find its title")
    public void i_should_be_able_to_find_its_title() {
        Assert.assertNotNull("First article title is not found", newsArticle.getTitle());
        Assert.assertTrue("First article title is empty", newsArticle.getTitle().trim().length() > 0);
    }

    @And("I should search for similar information on {string}")
    public void i_should_search_for_similar_information_on(String BingSearchEngine ) {
        String bingSearchUrl = BingSearchEngine  + newsArticle.getTitle();
        driver.get(bingSearchUrl);
    }

    @Then("the article is valid if there are multiple matching information")
    public void the_article_is_valid_if_there_are_multiple_matching_information(){
        Assert.assertTrue("Headline is likely to be fake", newsArticle.isAuthentic());
    }

    @After
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
