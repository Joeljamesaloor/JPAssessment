package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class CustomDriver {

    public static WebDriver getDriver() throws IOException {
        WebDriver driver;
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/test/java/config/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String browser = props.getProperty("browser");
        switch (browser.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", Config.CHROMEDRIVER_PATH);
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", Config.GECKODRIVER_PATH);
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setCapability("marionette", true);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            default:
                throw new IllegalArgumentException("Invalid browser name: " + browser);
        }
        return driver;
    }
}
