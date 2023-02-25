import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GuardianNewsArticleStepDefs {
    private WebDriver driver;
    private String headline;


    @Given("^I am on The Guardian website$")
    public void i_am_on_the_guardian_website() {
        System.setProperty("webdriver.chrome.driver", Config.CHROMEDRIVER_PATH);
        driver = new ChromeDriver();
        driver.get("https://www.theguardian.com/tone/news");
       /* driver.switchTo().frame("sp_message_iframe_770603");
        driver.findElement(By.xpath("//button[@title='Yes, I’m happy']")).click();
        driver.switchTo().defaultContent();
        */
        if(driver.findElements(By.id("sp_message_iframe_770603")).size() > 0) {
            driver.switchTo().frame("sp_message_iframe_770603");
            WebElement acceptButton = driver.findElement(By.xpath("//button[@title='Yes, I’m happy']"));
            acceptButton.click();
            driver.switchTo().defaultContent();
        }
        driver.findElement(By.xpath("//a[@class='pillar-link pillar-link--News']")).click();
        WebElement element = driver.findElement(By.xpath("//a[@class='u-faux-block-link__overlay js-headline-text']"));
        headline = element.getAttribute("innerText");
        System.out.println(headline);

    }

    @When("^I retrieve the first news article$")
    public void i_retrieve_the_first_news_article() {
        WebElement element = driver.findElement(By.xpath("//a[@class='u-faux-block-link__overlay js-headline-text']"));
         headline = element.getAttribute("innerText");
        System.out.println(headline);

    }

    @When("^search for title in Google$")
    public void search_for_title_in_google() throws IOException {
        /*driver.get("https://www.google.com/");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='Accept all']")));
        driver.findElement(By.xpath("//div[text()='Accept all']")).click();
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(headline);
        searchBox.submit();
         */

        //InputStream modelIn = new FileInputStream("resources/sent.model");
        InputStream modelIn = new FileInputStream("src/main/resources/en-sent.bin");
        SentenceModel model = new SentenceModel(modelIn);
        SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);

        driver.get("https://www.bing.com/search?q=" + headline );


// Get the search result elements
        List<WebElement> searchResultElements = driver.findElements(By.xpath("//*[@id='b_results']//li[@class='b_algo']"));
        double threshold = 0.8;
        int count = searchResultElements.size();
        System.out.println("Number of search result elements: " + count);

// Create a JaroWinkler similarity calculator
        JaroWinklerSimilarity jws = new JaroWinklerSimilarity();

// Loop through the search result elements and compare each result with the headline text
        boolean isHeadlineAuthentic = false;
        for (WebElement searchResultElement : searchResultElements) {
            // Get the text of the search result element
            String searchResultText = searchResultElement.getAttribute("innerText");

            // Calculate the similarity score between the search result text and the headline
            double similarityScore = jws.apply(searchResultText, headline);

            // Check if the similarity score is above the threshold
            if (similarityScore > threshold) {
                // At least one search result has a similarity score above the threshold, so the headline is likely to be authentic
                isHeadlineAuthentic = true;
                break;
            }
        }
        // Assert that the headline is not fake based on the similarity score threshold
        Assert.assertTrue("Headline is likely to be fake",isHeadlineAuthentic);


    }



    @After
    public void closeBrowser() {
        driver.quit();
    }

}
