package steps;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class Tests {

    public WebDriver driver;

    @Given("^user is on the main page$")
    public void user_is_on_homepage() throws Throwable {
        driver = Hooks.driver;
        driver.get("https://coinmarketcap.com/");
    }

    @When("^user clicks on (.*)$")
    public void user_navigates_to_Login_Page(String link) throws Throwable {
        driver.findElement(By.linkText(link)).click();
    }

    @Then("^a (.*) results are displayed$")
    public void results_displayed(int results) throws Throwable {
        List<WebElement> rows;
        rows = driver.findElement(By.className("cmc-table__table-wrapper-outer")).
                findElement(By.xpath(".//tbody")).
                findElements(By.xpath(".//tr"));
        System.out.println(rows);
        Assert.assertEquals(results, rows.size());
    }

    @Then("^more then (.*) results are displayed$")
    public void more_results_displayed(int results) throws Throwable {
        List<WebElement> rows;
        rows = driver.findElement(By.className("cmc-table__table-wrapper-outer")).
                findElement(By.xpath("//tbody")).
                findElements(By.xpath("//tr"));
        System.out.println(rows);
        Assert.assertTrue(results < rows.size());
    }
}
