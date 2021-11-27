package scrapper;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeleniumScrapper {

    private static final String BASE_URL = "https://www.tokopedia.com/p/handphone-tablet/handphone?ob=5&page=1";
    private static String itemXpath = "//div[@class='css-bk6tzz e1nlzfl3']/a";

    public static void main(String[] args) {
        scrapePage();
    }

    private static void scrapePage () {
        System.setProperty("webdriver.chrome.driver","D:\\Project\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get(BASE_URL);
        System.out.println(BASE_URL);
        driver.manage().timeouts().implicitlyWait(12, TimeUnit.SECONDS);
        // Javascript executor
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
        System.out.println(driver.getTitle());
        List<WebElement> itemList = driver.findElements(By.xpath(itemXpath));
        for (WebElement item : itemList) {
            String test = item.getText();
            System.out.println(test);
        }
//        WebElement element = driver.findElement();
        driver.quit();
    }

}
