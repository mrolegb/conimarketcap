package steps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import static steps.Helper.*;

public class Tests {

    public WebDriver driver;
    public List<String> myCurrencies = new ArrayList<String>();

    @Given("^the user is on the main page$")
    public void user_is_on_homepage() throws Throwable {
        driver = Hooks.driver;
        driver.get("https://coinmarketcap.com/");
        closeAllCrap(driver);
    }

    @When("^the user clicks on (.*)$")
    public void user_navigates_to_Login_Page(String link) throws Throwable {
        driver.findElement(By.linkText(link)).click();
        Thread.sleep(1000);
    }

    @Then("^a (.*) results are displayed$")
    public void results_displayed(int results) throws Throwable {
        List<WebElement> rows;
        rows = getCurrencyRows(driver);
        Assert.assertEquals(results, rows.size());
    }

    @Then("^more then (.*) results are displayed$")
    public void more_results_displayed(int results) throws Throwable {
        List<WebElement> rows;
        rows = getCurrencyRows(driver);
        Assert.assertTrue(results < rows.size());
    }

    @When("^the user adds (.*) items to the Watchlist$")
    public void add_random_to_watchlist(int number) throws Throwable {
        List<Integer> numbers = getNRandoms(number);
        List<WebElement> rows;
        rows = getCurrencyRows(driver);
        for (Integer integer : numbers) {
            WebElement row = rows.get(integer);
            myCurrencies.add(getRowCurrency(row));
            addToWatchlist(driver, row);
        }
    }

    @Then("^all the currencies are in the Watchlist$")
    public void check_watchlist() throws Throwable {
        List<String> watchlist = new ArrayList<String>();
        List<WebElement> rows = getCurrencyRows(driver);
        for (WebElement row : rows) {
            watchlist.add(getRowCurrency(row));
        }
        Assert.assertTrue(watchlist.containsAll(myCurrencies));
    }
}
