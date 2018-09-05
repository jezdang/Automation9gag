package com.automation.pageobjects;

import com.automation.utils.ExtendLog;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePageObject {

    @FindBy(id = "login-email-name")
    private WebElement loginEmailTextbox;

    @FindBy(id = "login-email-password")
    private WebElement loginPasswordTextbox;

    @FindBy(xpath = ".//input[@type='submit']")
    private WebElement loginButton;

    public void enterLoginEmail(String email){
        ExtendLog.action("Enter email: " + email);
        loginEmailTextbox.sendKeys(email);
    }

    public void enterLoginPassword(String password){
        ExtendLog.action("Enter password");
        loginPasswordTextbox.sendKeys(password);
    }

    public void clickLoginButton(){
        ExtendLog.action("Click Login Button");
        loginButton.click();
    }

    public HomePage loginToHomePage(String email, String password){
        enterLoginEmail(email);
        enterLoginPassword(password);
        clickLoginButton();
        return new HomePage();
    }

}
