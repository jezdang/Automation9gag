package com.automation.pageobjects;

import com.automation.helper.JSComponentHelper;
import com.automation.helper.WebElementHelper;
import com.automation.utils.Constants;
import com.automation.utils.LoadingConstants;
import com.automation.utils.SharedDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePageObject extends SharedDriver {

    protected static final String STR_TITLE_WEBSITE = "9GAG: GO FUN THE WORLD";

    @FindBy(css=".logo")
    protected WebElement img9GagLogo;

    @FindBy(css=".stealthy-scroll-container")
    protected WebElement scrollContainer;

    @FindBy(xpath = ".//*[@id='overlay-contianer']//a[.=\"I'll get it later\"]")
    protected WebElement getItLaterLink;

    protected WebElement getPageTitle() {
        return WebElementHelper.waitAndGetElement(By.tagName("title"), LoadingConstants.FRAME_TIMEOUT);
    }

    public BasePageObject(){
        PageFactory.initElements(real_driver, this);
    }

    protected void checkPage() {
        //TODO: DEFINE HOW TO CHECK PAGE
    }

    protected void waitForPageLoad() {
        waitForPageLoad(real_driver);
    }

    protected void waitForPageLoad(WebDriver driver) {
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public HomePage launchHomePage() {
        if(real_driver.getTitle().isEmpty() || !real_driver.getTitle().equalsIgnoreCase(STR_TITLE_WEBSITE)) {
            real_driver.navigate().to(Constants.URL_HOMEPAGE_9GAG);
        }
        waitForPageLoad();
        return new HomePage();
    }

    public void clickGetItLater(){
        if(getItLaterLink.isDisplayed()) {
            getItLaterLink.click();
        }
        WebElementHelper.waitForElementNotExist(By.xpath("//*[@id='overlay-contianer']"), Constants.LOADING_TIMEOUT);
    }
}
