package pageFactory;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class GooglePage {

    WebDriver driver;

    public GooglePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//input[@title=\"Search\"]")
    private WebElement inputBox;

    @FindBy(xpath = "//div[@class=\"TbwUpd NJjxre\"]")
    private List<WebElement> searchURL;

    @FindBy(xpath = "//span[contains(text(),\"Next\")]")
    private WebElement nextButton;


    private void fillText(WebElement textBox, String loc1) {
        textBox.click();
        textBox.clear();
        textBox.sendKeys(loc1);
    }

    public void search(String searchText){
        fillText(inputBox,searchText);
        inputBox.submit();
    }

    private Boolean verifyTopResult(){
        Boolean result = false;
        if(searchURL.get(0).getText().contains("www.instawork.com"))
            result = true;
        return result;
    }

    private int otherIndex(){
        int index = 0;
        for(int i=1;i<searchURL.size();i++){        //Starting from 1 as top result is already checked
            if(searchURL.get(i).getText().contains("www.instawork.com")) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void checkResult(){
        Boolean isTopResult;
        Boolean instaworkFound = false;
        WebDriverWait wait = new WebDriverWait(driver,10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.visibilityOf(searchURL.get(0)));
        isTopResult = verifyTopResult();
        if(isTopResult) {
            System.out.print("Instawork shown as top result");
            instaworkFound = true;
        }
        else {
            int pageNumber = 1;
            int resultNumber = otherIndex();
            if (resultNumber != 0) {
                System.out.print("Instawork is shown at page : " + pageNumber + " at index :" + (resultNumber + 1));
                instaworkFound = true;
            }
            else {
                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                try{
                    while(nextButton.isDisplayed()) {
                        nextButton.click();
                        pageNumber++;
                        wait.until(ExpectedConditions.visibilityOf(searchURL.get(0)));
                        resultNumber = otherIndex();
                        if(resultNumber!=0) {
                            System.out.print("Instawork is shown at page : " + pageNumber + " at index :" + (resultNumber + 1));
                            instaworkFound = true;
                            break;
                        }
                    }
                }catch (Exception e){
                    System.out.println("Reached the end of search result pages");
                }
            }
        }
        if(!instaworkFound)
            System.out.print("Instawork not found");
    }
}
