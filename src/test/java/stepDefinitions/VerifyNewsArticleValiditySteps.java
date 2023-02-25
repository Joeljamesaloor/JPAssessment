package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.*;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import config.Config;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class VerifyNewsArticleValiditySteps {

    private WebDriver driver;
    private String firstArticleTitle;

    boolean isHeadlineAuthentic = false;

    @Given("I am on {string}")
    public void i_am_on(String newsWebsite) {
        System.setProperty("webdriver.chrome.driver", Config.CHROMEDRIVER_PATH);
        driver = new ChromeDriver();
        driver.get(newsWebsite);
    }

    @When("I read the first news article")
    public void i_read_the_first_news_article() {

        if(driver.findElements(By.id("sp_message_iframe_770603")).size() > 0) {
            driver.switchTo().frame("sp_message_iframe_770603");
            WebElement acceptButton = driver.findElement(By.xpath("//button[@title='Yes, I’m happy']"));
            acceptButton.click();
            driver.switchTo().defaultContent();
        }
        driver.findElement(By.xpath("//a[@class='pillar-link pillar-link--News']")).click();
        WebElement element = driver.findElement(By.xpath("//a[@class='u-faux-block-link__overlay js-headline-text']"));
        firstArticleTitle = element.getAttribute("innerText");
       // System.out.println(firstArticleTitle);
    }

    @Then("I should be able to find its title")
    public void i_should_be_able_to_find_its_title() {
        assertNotNull("First article title is not found", firstArticleTitle);
        assertTrue("First article title is empty", firstArticleTitle.trim().length() > 0);
    }

    @And("I should search for similar information on {string}")
    public void i_should_search_for_similar_information_on(String BingSearchEngine ) {
        //String searchQuery = firstArticleTitle.replace(" ", "+");
        String bingSearchUrl = BingSearchEngine  + firstArticleTitle;
        driver.get(bingSearchUrl);
    }

    @Then("if I find two or more articles with similar information")
    public void if_I_find_two_or_more_articles_with_similar_information(){

        // Get the search result elements
        List<WebElement> searchResultElements = driver.findElements(By.xpath("//*[@id='b_results']//li[@class='b_algo']"));
        double threshold = 0.8;
        //int count = searchResultElements.size();
        //System.out.println("Number of search result elements: " + count);

        // Create a JaroWinkler similarity calculator
        JaroWinklerSimilarity jws = new JaroWinklerSimilarity();

        // Loop through the search result elements and compare each result with the headline text
        for (WebElement searchResultElement : searchResultElements) {
            // Get the text of the search result element
            String searchResultText = searchResultElement.getAttribute("innerText");

            // Calculate the similarity score between the search result text and the headline
            double similarityScore = jws.apply(searchResultText, firstArticleTitle);

            // Check if the similarity score is above the threshold
            if (similarityScore > threshold) {
                // At least one search result has a similarity score above the threshold, so the headline is likely to be authentic
                isHeadlineAuthentic = true;
                break;
            }
        }

    }

    @Then("I should consider the first Guardian news article as not Fake")
    public void i_should_consider_the_first_Guardian_news_article_as_not_Fake() {
        // Assert that the headline is not fake based on the similarity score threshold
        Assert.assertTrue("Headline is likely to be fake",isHeadlineAuthentic);
    }

    @After
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
