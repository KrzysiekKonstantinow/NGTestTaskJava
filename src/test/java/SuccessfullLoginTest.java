import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class SuccessfullLoginTest {
    @Test
    public void successfullLoginTest() {
        System.setProperty("webdriver.chrome.driver", "chromedriver//chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.reserved.com/pl/pl/customer/account/login/#login");

        WebElement username = driver.findElement(By.cssSelector("[data-selen=login-email]"));
        WebElement password = driver.findElement(By.cssSelector("[data-selen=login-password]"));
        WebElement login = driver.findElement(By.cssSelector("[data-selen=login-submit]"));

        Wait wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(3, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        username.sendKeys("NGTestTask@mailinator.com");
        password.sendKeys("123456");
        login.click();

        WebElement element = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id("cartRoot"));
            }
        });

        String expectedUrl="https://www.reserved.com/pl/pl/checkout/cart/";
        String actualUrl= driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);

        driver.quit();
    }
}