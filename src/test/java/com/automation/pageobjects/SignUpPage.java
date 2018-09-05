package com.automation.pageobjects;

import com.automation.pageobjects.BasePageObject;
import com.automation.helper.WebElementHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpPage extends BasePageObject {

    @FindBy(xpath = ".//*[@id='signup-fb']//a[text()='Email Address']")
    WebElement emailAddressLink;

    @FindBy(id = "signup-email-name")
    WebElement fullNameTextbox;

    @FindBy(id = "signup-email-email")
    WebElement emailTextbox;

    @FindBy(id = "signup-email-password")
    WebElement passwordTextbox;

    @FindBy(xpath = ".//input[@type='submit']")
    WebElement signupButton;

    public void clickEmailAddress(){
        emailAddressLink.click();
        WebElementHelper.waitForElementExist(By.id("signup"));
    }

    public void enterFullName(String fullName){
        fullNameTextbox.sendKeys(fullName);
    }

    public void enterEmail(String email){
        emailTextbox.sendKeys(email);
    }

    public void enterPassword(String password){
        passwordTextbox.sendKeys(password);
    }

    public void clickSignUpButton(){
        signupButton.click();
    }

    public void fillNewMemberInfo(String fullName, String email, String password){
        enterFullName(fullName);
        enterEmail(email);
        enterPassword(password);
        clickSignUpButton();
    }

}
