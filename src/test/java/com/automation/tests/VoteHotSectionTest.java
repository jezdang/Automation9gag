package com.automation.tests;

import com.automation.pageobjects.*;
import com.automation.utils.Constants;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class VoteHotSectionTest {

    private HomePage homePage;

    @BeforeTest
    public void launchHomePage(){
        BasePageObject basePage = new BasePageObject();
        homePage = basePage.launchHomePage();
    }

    @Test
    public void voteHotSectionTest(){
        homePage.isUserNotLoggedIn();
        LoginPage loginPage = homePage.clickLogin();
        homePage = loginPage.loginToHomePage(Constants.EMAIL_LOGIN, Constants.PASSWORD_LOGIN);
        homePage.isUserLoggedIn();
        HotSectionPage hotSectionPage = homePage.goToHotSectionPage();
        hotSectionPage.clickUpVoteByIndex(1);
        hotSectionPage.verifyArticleUpVotedByIndex(1);
    }
}
