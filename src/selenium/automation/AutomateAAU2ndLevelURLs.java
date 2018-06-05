package selenium.automation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AutomateAAU2ndLevelURLs {
    private WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver","/usr/local/share/chromedriver");
        driver = new ChromeDriver();
        baseUrl = "http://www.aait.edu.et/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testUntitledTestCase() throws Exception {
        driver.get(baseUrl);

        List<WebElement> linkList = clickableLinks(driver);

        linkList = secondLevelURLs(linkList);

        for(WebElement link : linkList){
            String href =  link.getAttribute("href") != null ?
                    link.getAttribute("href") :  link.getAttribute("src");

            try{
                System.out.println("URL " + href + " | Status: " + linkStatus(new URL(href))
                        + " | Lebal: " + typeOfLink(href));
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public static List<WebElement> clickableLinks(WebDriver driver){
        List<WebElement> linksToClick = new ArrayList<WebElement>();
        List<WebElement> elements = driver.findElements(By.tagName("a"));
        elements.addAll(driver.findElements(By.tagName("img")));

        for(WebElement e : elements){
            if(e.getAttribute("href") != null | e.getAttribute("src") != null){
                linksToClick.add(e);
            }
        }
        return linksToClick;
    }

    public static String linkStatus(URL url){
        try {
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setConnectTimeout(3000);
            http.connect();
            String responseMessage;
            if(http.getResponseCode() == 200){
                responseMessage = "Active";
            } else {
                responseMessage = "Inactive";
            }
            http.disconnect();
            return responseMessage;
        } catch (Exception e) {
            return "Inactive";
        }
    }

    public static List<WebElement> secondLevelURLs(List<WebElement> links){
        List<WebElement> secLevURLs = new ArrayList<WebElement>();
        for(WebElement link : links){
            String href =  link.getAttribute("href") != null ?
                    link.getAttribute("href") :  link.getAttribute("src");

            String[] splitedLink = href.split("/");

            if(splitedLink.length == 4 && splitedLink[2].equals("www.aait.edu.et")){
                secLevURLs.add(link);
            }
        }

        return secLevURLs;
    }

    public static String typeOfLink(String link){

        String[] pdf = new String[] {"pdf", "PDF"};
        String[] img = new String[] {"jpg", "JPG", "png", "PNG"};

        String[] splitedLink = link.split("/");

        if(Arrays.asList(pdf).contains(splitedLink[splitedLink.length - 1])){
            return "PDF";
        } else if(Arrays.asList(img).contains(splitedLink[splitedLink.length - 1])){
            return  "IMAGE";
        } else {
            return "ORDINARY URL";
        }
    }


    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}
