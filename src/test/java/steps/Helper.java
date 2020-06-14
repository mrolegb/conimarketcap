package steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    public static List<WebElement> getCurrencyRows(WebDriver driver) {
        List<WebElement> rows;
        rows = driver.findElement(By.className("cmc-table__table-wrapper-outer")).
                findElement(By.xpath("//tbody")).
                findElements(By.xpath("./tr"));

        return rows;
    }

    public static void addToWatchlist(WebDriver driver, WebElement currencyRow) {
        currencyRow.findElement(By.className("cmc-popover__trigger")).click();
        List<WebElement> menu = driver.findElement(By.cssSelector("ul[class^='cmc-menu']")).
                findElements(By.tagName("li"));
        menu.get(0).click();
    }
    
    public static String getRowCurrency(WebElement currencyRow) {
        return currencyRow.findElement(By.cssSelector("div[class^='cmc-table__column-name']")).
                findElement(By.tagName("a")).getAttribute("title");
    }

    public static void closeAllCrap(WebDriver driver) {
        driver.findElement(By.className("cmc-chat-intro-popup__contents")).findElement(By.tagName("button")).click();
        driver.findElement(By.className("cmc-cookie-policy-banner__close")).click();
    }

    public static List<Integer> getNRandoms(int max){
        List<Integer> numbers = new ArrayList<Integer>();
        do {
            int i = (int)(Math.random() * max);
            if(!numbers.contains(i))
                numbers.add(i);
        } while (numbers.size() < max);

        return numbers;
    }
}
