package tests.maps;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageFactory.GooglePage;
import tests.BaseTest;

public class SearchResultIndex extends BaseTest {

    @BeforeMethod
    public void setUp(){
        googlePage = new GooglePage(driver);
    }

    @Test
    public void resultIndex() {
        googlePage.search(prop.getProperty("Search_Keyword"));
        googlePage.checkResult();
    }
}
