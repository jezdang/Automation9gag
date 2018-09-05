package com.automation.pageobjects;

import com.automation.helper.WebElementHelper;
import com.automation.utils.ExtendLog;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.testng.Assert.assertTrue;

public class HotSectionPage extends BasePageObject {

    private static final String ARTICLE_XPATH = ".//*[contains(@id,'jsid-post')]";

    /**
     *
     * @param index start from 1
     */
    public void clickUpVoteByIndex(int index){
        ExtendLog.action("Click up vote on article index: " + index);
        WebElement articleElement = WebElementHelper.findElements(By.xpath(ARTICLE_XPATH)).get(index-1);
        articleElement.findElement(By.className("up")).click();
    }

    public boolean isUpVotedByIndex(int index){
        WebElement articleElement = WebElementHelper.findElements(By.xpath(ARTICLE_XPATH)).get(index-1);
        return !articleElement.findElements(By.cssSelector(".up.active")).isEmpty();
    }

    public void verifyArticleUpVotedByIndex(int index){
        ExtendLog.action(String.format("Verify article index [%s] is up voted", index));
        assertTrue(isUpVotedByIndex(index));
    }

}
