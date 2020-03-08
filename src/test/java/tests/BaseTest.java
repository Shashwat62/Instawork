package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import pageFactory.GooglePage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;


public class BaseTest {

    protected WebDriver driver;
    protected GooglePage googlePage;
    protected static Properties prop;

    @BeforeSuite
    void initSetup(){
        System.setProperty("webdriver.chrome.driver", "chromedriver");
    }

    @BeforeTest
    public void initDriver(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://www.google.com");
    }

    @BeforeTest
    public void getSearchKeyword(){
        prop = new Properties();
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader("src/main/resources/SearchKeyword.properties"));
            prop.load(reader);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @AfterTest
    public void closeDriver(){
        driver.close();
    }
}
