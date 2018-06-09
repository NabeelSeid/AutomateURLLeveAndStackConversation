package selenium.automation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class AutomateSlackConversation {
    private WebDriver driver;
    private String baseUrl;
    private JavascriptExecutor js;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver","/usr/local/share/chromedriver");
        driver = new ChromeDriver();
        baseUrl = "https://www.slack.com/";
        js = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testEmailNotificationTestCase() throws Exception {
        driver.get(baseUrl);
        WebElement signInLink = driver.findElement(
                By.xpath("//p[@class='u-margin-bottom--flush u-margin-top--medium display-as-mini']//a[@href='https://slack.com/signin']"));
        signInLink.click();

        WebElement workspaceInput = driver.findElement(By.xpath("//input[@id='domain']"));
        workspaceInput.sendKeys("aaititsc");

        WebElement contBtn = driver.findElement(By.xpath("//button[@id='submit_team_domain']"));
        contBtn.click();

        WebElement emailInput = driver.findElement(By.xpath("//input[@id='email']"));
        emailInput.sendKeys("username");

        WebElement passInput = driver.findElement(By.xpath("//input[@id='password']"));
        passInput.sendKeys("password");

        driver.findElement(By.xpath("//button[@id='signin_btn']")).click();

        WebElement slackBot = driver.findElement(By.xpath("//a[@href='/messages/DAA8ZL2SV']"));
        slackBot.click();

        driver.findElement(By.xpath("//div[@id='msg_input']/div/p")).click();

        js.executeScript("var i = document.evaluate('//div[@id=\\'msg_input\\']/div/p', " +
                "document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue; " +
                "i.innerText = 'Hi..." + new java.util.Date() + "';");

        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);
    }

    @After
    public void tearDown() throws Exception {
        //driver.quit();
    }
}
