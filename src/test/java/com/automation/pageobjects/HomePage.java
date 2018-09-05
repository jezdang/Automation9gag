package com.automation.pageobjects;

import com.automation.helper.WebElementHelper;
import com.automation.utils.ExtendLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class HomePage extends BasePageObject {

    @FindBy(id = "jsid-login-button")
    protected WebElement loginButton;

    @FindBy(id = "jsid-signup-button")
    protected WebElement signupButton;

    @FindBy(className = "user-function")
    protected WebElement userMenu;

    @FindBy(xpath = ".//a[@href='/hot']")
    protected WebElement hotSectionLink;

    public boolean isHomePageDisplayedAgain() {
        WebElementHelper.waitForElementExist(By.className("logo"));
        return img9GagLogo.isDisplayed();
    }

    public LoginPage clickLogin() {
        loginButton.click();
        return new LoginPage();
    }

    public SignUpPage clickSignUp() {
        signupButton.click();
        return new SignUpPage();
    }

    public void isUserLoggedIn() {
        ExtendLog.check("Verify User is logged in");
        assertTrue(userMenu.isDisplayed());
    }

    public void isUserNotLoggedIn() {
        ExtendLog.check("Verify User is not logged in");
        assertFalse(userMenu.isDisplayed());
    }

    public HotSectionPage goToHotSectionPage() {
        hotSectionLink.click();
        return new HotSectionPage();
    }
}
