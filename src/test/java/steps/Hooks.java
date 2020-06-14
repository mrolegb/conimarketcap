package steps;

import cucumber.api.java.Before;
import cucumber.api.java.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Hooks {

    public static WebDriver driver;
    public static List<String> myCurrencies;

    @Before
    public void setUp(){
        myCurrencies = new ArrayList<String>();

        System.setProperty("webdriver.gecko.driver","/usr/local/bin/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @After
    public void tearDown(){
        driver.quit();
    }
}
