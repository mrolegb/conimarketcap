package steps;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import static steps.Helper.*;
import static steps.Hooks.myCurrencies;

public class Tests {

    public WebDriver driver;

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
        List<WebElement> rows = getCurrencyRows(driver);
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

    @When("^they open the full list$")
    public void open_full_list() throws Throwable {
        driver.findElement(By.className("cmc-tabs__header")).findElement(By.className("cmc-popover__trigger")).click();
        List<WebElement> menu = driver.findElement(By.className("cmc-popover__dropdown")).
                findElements(By.tagName("li"));
        menu.get(2).click();
        Thread.sleep(500);
    }

    @When("^record (.*) top currencies$")
    public void store_top(int number) throws Throwable {
        List<WebElement> rows = getCurrencyRows(driver);
        for (int i=0; i < number; i++) {
            WebElement row = rows.get(i);
            myCurrencies.add(getRowCurrency(row));
        }
    }

    @When("^apply the price filter$")
    public void apply_price_filter() throws Throwable {
        driver.findElement(By.cssSelector("button[class^='cmc-table-listing__filter-button']")).click();
        List<WebElement> filters = driver.findElement(By.className("cmc-table-listing__filters")).
                findElements(By.tagName("button"));
        filters.get(1).click();
        driver.findElement(By.name("priceMin")).sendKeys("10000");
        Thread.sleep(500);
        List<WebElement> buttons = driver.findElement(By.className("cmc-filter-dd__footer__right")).
                findElements(By.tagName("button"));
        buttons.get(1).click();
        Thread.sleep(500);
    }

    @Then("^top (.*) currencies are different$")
    public void compare_after_filter(int number) throws Throwable {
        List<WebElement> rows = getCurrencyRows(driver);
        List<String> currencies = new ArrayList<String>();
        for (int i=0; i < number; i++) {
            WebElement row = rows.get(i);
            currencies.add(getRowCurrency(row));
        }
        Assert.assertFalse(currencies.containsAll(myCurrencies));
    }

}
