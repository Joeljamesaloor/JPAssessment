package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Config {

    //public static final String CHROMEDRIVER_PATH = "/usr/local/chromedriver";
   // public static final String GECKODRIVER_PATH = "/usr/local/geckodriver";
    public static final String CHROMEDRIVER_PATH = "src/main/resources/drivers/chromedriver";
    public static final String GECKODRIVER_PATH = "src/main/resources/drivers/geckodriver";

}
