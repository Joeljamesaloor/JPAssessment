package pages;

import config.CustomDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToNewsSection(String newsWebsite) {
        driver.get(newsWebsite);
        if(driver.findElements(By.id("sp_message_iframe_770603")).size() > 0) {
            driver.switchTo().frame("sp_message_iframe_770603");
            driver.findElement(By.cssSelector("button[title='Yes, I’m happy']")).click();
            driver.switchTo().defaultContent();
        }
        driver.findElement(By.cssSelector("a.pillar-link.pillar-link--News")).click();
    }
}
