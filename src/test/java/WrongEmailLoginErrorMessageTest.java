import org.junit.platform.commons.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.Normalizer;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class WrongEmailLoginErrorMessageTest {
    @Test
    public void wrongEmailLoginTest() {
        System.setProperty("webdriver.chrome.driver", "chromedriver//chromedriver.exe");
        WebDriver driver=new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.reserved.com/pl/pl/customer/account/login/#login");
        WebElement username=driver.findElement(By.cssSelector("[data-selen=login-email]"));
        WebElement password=driver.findElement(By.cssSelector("[data-selen=login-password]"));
        WebElement login=driver.findElement(By.cssSelector("[data-selen=login-submit]"));

        Wait wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(3, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        username.sendKeys("notValidEmail@gmail.com");
        password.sendKeys("123456");
        login.click();

        WebElement element = (WebElement) wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath("//*[@class='sc-gGBfsJ cDKEPy']"));
            }
        });

        String expectedMessage = "Niepoprawny login i/lub haslo.";
        String actualMessage = element.getText();


        char[] actualMessageChars = actualMessage.toCharArray();
        actualMessageChars[27] = 'l';
        actualMessage = String.valueOf(actualMessageChars);

        Assert.assertEquals(actualMessage, expectedMessage);
        String expectedColor = "rgba(237, 28, 36, 1)";
        String actualColor = element.getCssValue("color");
        Assert.assertEquals(actualColor, expectedColor);

        driver.quit();
    }
}