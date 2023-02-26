package objects;

import config.CustomDriver;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NewsArticle {
    private String title;
    private final WebDriver driver;

    public NewsArticle(WebDriver driver) {
        this.driver = driver;
    }

    public void readFirstArticle() {
        WebElement element = driver.findElement(By.xpath("//a[@class='u-faux-block-link__overlay js-headline-text']"));
        this.title = element.getAttribute("innerText");
    }

    public boolean isAuthentic() {
        double threshold = 0.8;
        List<WebElement> searchResultElements = driver.findElements(By.xpath("//*[@id='b_results']//li[@class='b_algo']"));
        JaroWinklerSimilarity jws = new JaroWinklerSimilarity();
        for (WebElement searchResultElement : searchResultElements) {
            String searchResultText = searchResultElement.getAttribute("innerText");
            double similarityScore = jws.apply(searchResultText, this.title);
            if (similarityScore > threshold) {
                return true;
            }
        }
        return false;
    }

    public String getTitle() {
        return this.title;
    }
}
