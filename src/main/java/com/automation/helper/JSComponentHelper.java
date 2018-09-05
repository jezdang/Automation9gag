package com.automation.helper;

import com.automation.utils.DriverFactory;
import com.automation.utils.ExtendLog;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;

/**
 * This class helps to work with javascript. Eg. execute a javascript command, getId, click, blur
 */
public class JSComponentHelper {

	public static String getValueJS(final String id) {
		if (StringUtils.isNotBlank(id)) {
			return String.valueOf(executeJavaScript("return document.getElementById('" + id + "').value;"));
		} else {
			return null;
		}
	}

	public static String getValueJS(final WebElement webElement) {
		final String id = getId(webElement);
		if (StringUtils.isNotBlank(id)) {
			return String.valueOf(executeJavaScript("return document.getElementById('" + id + "').value;"));
		} else {
			return null;
		}
	}

	public static void setValueJS(final WebElement webElement, String value) {
		final String id = getId(webElement);
		setValueJS(id, value);
	}

	public static void setValueJS(final String id, String value) {
		if (StringUtils.isNotBlank(id)) {
			String scriptText = XpathHelper.formatXpath("return document.getElementById('%s').value='%s';", value);
			executeJavaScript(String.format(scriptText, id, value));
		} else {
			throw new InvalidElementStateException("The element should have an id to be set with this method");
		}
	}

	public static void setValueJS(final By locator, String value) {
		setValueJS(WebElementHelper.waitAndGetElement(locator), value);
	}

	public static void clearAndSetValueJS(final By locator, String value) {
		WebElement element = WebElementHelper.waitAndGetElement(locator);
		element.clear();
		setValueJS(element, value);
	}

	public static void setValueAndTab(final WebElement webElement, String value) {
		setValueJS(webElement, value);
		webElement.sendKeys(Keys.TAB);
	}

	public static String getInnerHtmlForElementWithId(final String id) {
		return String
				.valueOf(JSComponentHelper.executeJavaScript("return document.getElementById('" + id + "').innerHTML;"));
	}

	public static Object executeJavaScript(final String script) {
		AbstractObjectAssert<?, WebDriver> assertDriver = Assertions.assertThat(DriverFactory.getDriver());
		assertDriver.isNotNull();
		assertDriver.isInstanceOf(JavascriptExecutor.class);
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) DriverFactory.getDriver();
		return javascriptExecutor.executeScript(script);

	}

	public static String getId(WebElement webElement) {
		Assertions.assertThat(webElement).isNotNull();
		return webElement.getAttribute("id");
	}

	/**
	 * Blur the input element to have fields update
	 *
	 * @param webElement
	 */
	public static void blur(WebElement webElement) {
		final String id = getId(webElement);
		if (StringUtils.isNotBlank(id)) {
			executeJavaScript("return document.getElementById('" + id + "').blur();");
		}
	}

	/**
	 * Blur the input element to have fields update using EXT commands
	 *
	 * @param webElement
	 */
	public static void extBlur(WebElement webElement) {
		final String id = getId(webElement);
		if (StringUtils.isNotBlank(id)) {
			executeJavaScript("Ext.getCmp('" + id + "').fireEvent('blur');");
		}
	}

	public static void click(WebElement webElement) {
		final String id = getId(webElement);
		click(id);
	}

	public static void click(String id) {
		ExtendLog.info(String.format("Button id [%s]", id));
		if (StringUtils.isNotBlank(id)) {
			ExtendLog.info(String.format("Click by JavaScript on button id [%s]", id));
			executeJavaScript("return document.getElementById('" + id + "').click();");
		}
	}

	public static void hideMask() {
		executeJavaScript("stx_mask.hide(document)");
	}

	public static void refreshIframe(final String iframeId) {
		executeJavaScript(
				"document.getElementById('" + iframeId + "').src = document.getElementById('" + iframeId + "').src;");
	}

	public static void fireExtJsEvent(final String elementId, final String eventName, final String value) {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) DriverFactory.getDriver();
		String script = "Ext.getCmp('%s').fireEvent('%s', %s);";
		javascriptExecutor.executeScript(String.format(script, elementId, eventName, value));
	}

	public static void scrollToElementByID(String elementId) {
		scrollToElement(By.id(elementId));
	}

	public static void scrollToElement(By locator) {
		JavascriptExecutor je = (JavascriptExecutor) DriverFactory.getDriver();
		WebElement element = DriverFactory.getDriver().findElement(locator);
		je.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static void scrollToElement(WebElement webElement) {
		JavascriptExecutor je = (JavascriptExecutor) DriverFactory.getDriver();
		je.executeScript("arguments[0].scrollIntoView(true);", webElement);
	}

	public static void click(By locator) {
		JavascriptExecutor executor = (JavascriptExecutor) DriverFactory.getDriver();
		executor.executeScript("arguments[0].click()", DriverFactory.getDriver().findElement(locator));
	}
}
