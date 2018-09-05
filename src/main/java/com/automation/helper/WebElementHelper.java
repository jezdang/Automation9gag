package com.automation.helper;

import com.automation.utils.ExtendLog;
import com.automation.utils.LoadingConstants;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.automation.utils.DriverFactory.getDriver;


public class WebElementHelper {
	private static final Log LOGGER = LogFactory.getLog(WebElementHelper.class);

	// Hide the constructor
	private WebElementHelper() {
		super();
	}

	public static void printCurrentURL() {
		ExtendLog.info(String.format("Current URL: %s", getDriver().getCurrentUrl()));
	}

	public static void sendKeysOneByOne(final WebElement element, final String text) {
		final int contentLength = element.getText().length();

		new Actions(getDriver()).moveToElement(element).click().perform();// set
																			// focus

		new Actions(getDriver()).moveToElement(element)
				.sendKeys(
						Keys.chord(Keys.END, StringUtils.repeat(String.valueOf(Keys.BACK_SPACE), contentLength), text))
				.perform();

		WebElement parent = element.findElement(By.xpath(".."));
		parent.click();
		parent.sendKeys(Keys.TAB);
	}

	public static void sendKeysOneByOneLocated(final By locator, final String text) {
		sendKeysOneByOne(findElement(locator), text);
	}

	/**
	 * This method makes sure the text is filled in an input correctly. It only support html input with attribute
	 * "value".
	 *
	 * @param element
	 * @param text
	 */
	public static void sendKeysAndCheck(final WebElement element, final String text) {
		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(10, TimeUnit.SECONDS)
				.withMessage("Time out while trying to input text " + text);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(final WebDriver driver) {
				element.clear();
				element.sendKeys(text);
				JSComponentHelper.blur(element);
				if (text.equalsIgnoreCase(element.getAttribute("value"))) {
					return true;
				}
				ExtendLog.info("Expected text='" + text + "' but the value is '" + element.getAttribute("value")
						+ "'. Retry to fill text");
				return false;
			}
		});
	}

	/**
	 * Same as sendKeysAndCheck(element, text)
	 *
	 * @param by
	 * @param text
	 */
	public static void sendKeysAndCheckLocated(final By by, final String text) {
		final WebElement element = waitAndGetElement(by, LoadingConstants.RENDER_ELEMENT);
		sendKeysAndCheck(element, text);
	}

	/**
	 * Some input need a blur and tab event to update the value
	 *
	 * @param value
	 * @param inputElement
	 */
	public static void sendKeysAndBlur(final WebElement inputElement, final String value) {
		inputElement.clear();
		inputElement.sendKeys(value);
		JSComponentHelper.blur(inputElement);
		inputElement.sendKeys(Keys.TAB);
	}

	public static void sendKeysAndBlurNotClear(final WebElement inputElement, final String value) {
		inputElement.sendKeys(value);
		JSComponentHelper.blur(inputElement);
		inputElement.sendKeys(Keys.TAB);
	}

	public static void sendKeysAndBlurLocated(final By by, final String value) {
		final WebElement inputElement = waitAndGetElement(by, LoadingConstants.RENDER_ELEMENT);
		sendKeysAndBlur(inputElement, value);
	}

	/**
	 * Same as sendKeysAndCheck(element, text), but using a parent
	 *
	 * @param by
	 * @param text
	 */
	public static void sendKeysAndCheck(final WebElement parent, final By by, final String text) {
		final WebElement element = waitAndGetElement(parent, by, LoadingConstants.RENDER_ELEMENT);
		sendKeysAndCheck(element, text);
	}

	/**
	 * Wait and find element until it appears. This method intends to fix NoSuchElementException of
	 * http://jira-stx.elca.ch/jira/browse/STX-31933
	 *
	 * @param by
	 * @param timeout
	 * @return
	 */
	public static WebElement waitAndGetElement(final By by, long timeout) {
		waitForElementExist(by, timeout);
		return getDriver().findElement(by);
	}

	public static List<WebElement> waitAndGetElements(final By by, long timeout) {
		waitForElementExist(by, timeout);
		return getDriver().findElements(by);
	}

	public static List<WebElement> waitAndGetElements(final By by) {
		return waitAndGetElements(by, LoadingConstants.RENDER_ELEMENT);
	}

	/***
	 * Wait and find element until it appears. Default timeout = 60s
	 *
	 * @param by
	 * @return
	 */
	public static WebElement waitAndGetElement(final By by) {
		return waitAndGetElement(by, LoadingConstants.RENDER_ELEMENT);
	}

	/**
	 * Wait and find element from a parent until it appears. This method intends to fix NoSuchElementException of
	 * http://jira-stx.elca.ch/jira/browse/STX-31933
	 *
	 * @param by
	 *            : The locator of sub element. Note that if we use xpath, the prefix for finding is ".//"
	 * @param timeout
	 * @return
	 */
	public static WebElement waitAndGetElement(final WebElement parent, final By by, long timeout) {
		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS)
				.withMessage("Timeout while waiting element " + by);
		return wait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				List<WebElement> list = parent.findElements(by);
				return !list.isEmpty() ? findElement(parent, by) : null;
			}
		});
	}

	/**
	 * Some elements are never clickable. We can use {@link #waitAndGetElement(By)} then click().
	 */
	public static void waitAndClick(final By locator) {
		waitAndClick(locator, LoadingConstants.RENDER_ELEMENT);
	}

	/**
	 * Some elements are never clickable. We can use {@link #waitAndGetElement(By, long)} then click().
	 */
	public static void waitAndClick(final By locator, long timeout) {
		waitForElementExist(locator, timeout);
		waitForElementClickable(locator, timeout);
		click(getDriver().findElement(locator));
	}

	public static void waitForElementClickable(final By locator, long timeout) {
		new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static void waitForElementClickable(final By locator) {
		waitForElementClickable(locator, LoadingConstants.UPDATE_BUTTON);
	}

	public static void clickUntilOK(final By locator, long timeout, long poolingTime,
			final Predicate<WebDriver> condition) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(poolingTime, TimeUnit.SECONDS).withMessage("Failed to retry click.")
				.ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				if (condition.apply(driver)) {
					return true;
				}
				try {
					getDriver().findElement(locator).click();
				} catch (WebDriverException e) {
					ExtendLog.info("Cannot click the element because it disappears. Continue test." + e.getMessage());
					// return false;
				}
				return false;
			}
		});
	}

	/**
	 *
	 * avoid StaleElementReferenceException.
	 */
	public static WebElement findElement(final By by) {
		return getDriver().findElement(by);
	}

	/**
	 * Find and wrap element from a parent
	 */
	public static WebElement findElement(final WebElement parent, final By by) {
		return parent.findElement(by);
	}

	/**
	 * Find and wrap list elements
	 */
	public static List<WebElement> findElements(final By by) {
		return getDriver().findElements(by);
	}

	/**
	 * Find and wrap list elements from a parent
	 */
	public static List<WebElement> findElements(final WebElement parent, final By by) {
		return parent.findElements(by);
	}

	/**
	 *
	 * @param element
	 */
	public static void click(final WebElement element) {
				element.click();
	}

	public static void clickWithReleased(final WebElement element) {
		// To avoid "Cannot press more then one button or an already pressed
		// button"
		try {
			new Actions(getDriver()).release(element).perform();
		} catch (Exception e) {
			// No problem. Continue to click the element.
		}
		click(element);
	}

	public static void clickByJS(By locator) {
		JSComponentHelper.click(locator);
	}

	public static void clickByJS(WebElement element) {
		JSComponentHelper.click(element);
	}

	public static void click(final By locator) {
		click(findElement(locator));
	}

	/**
	 * Send key by TAB
	 */
	public static void sendKeysTab(final By locator) {
		findElement(locator).sendKeys(Keys.TAB);
	}

	public static void sendKeysTab(final WebElement inputElement) {
		inputElement.sendKeys(Keys.TAB);
	}

	/**
	 * Send key by Ctr+C
	 */
	public static void sendKeysCtrC(final WebElement inputElement) {
		inputElement.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.chord(Keys.CONTROL, "c"));
	}

	/**
	 * Send key by Ctr+V
	 */
	public static void sendKeysCtrV(final WebElement inputElement) {
		inputElement.clear();
		Actions action = new Actions(getDriver());
		inputElement.sendKeys(Keys.chord(Keys.CONTROL, "v"));
		action.build().perform();
	}

	public static void waitForElementExist(final By locator) {
		waitForElementExist(locator, LoadingConstants.LOADING_TIMEOUT);
	}

	public static void waitForElementExist(final By locator, final long timeout) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class)
				.withMessage(String.format("Timed out after %s seconds waiting for element %s", timeout, locator));
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return !getDriver().findElements(locator).isEmpty();
			}
		});
	}

	public static void waitForAnyElementExist(final By... locators) {
		waitForAnyElementExist(10l, locators);
	}

	public static void waitForAnyElementExist(final long timeout, final By... locators) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				for (By locator : locators) {
					if (!getDriver().findElements(locator).isEmpty()) {
						return true;
					}
				}
				return false;
			}
		});
	}

	public static void waitForElementNotExist(final By locator) {
		waitForElementNotExist(locator, LoadingConstants.LOADING_TIMEOUT);
	}

	/**
	 * Use MaskPredicate when checking non-exist element to keep tests stable. We should not over-use the methods
	 * waitForMask, waitForLoad. Otherwise, the test takes more time for nothing.
	 */
	public static void waitForElementNotExist(final By locator, final long timeout) {
		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class);
		wait.until(new MaskPredicate(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return isElementInvisible(locator);
			}
		}));
		LOGGER.info(
				"[END wait for element invisible]. Please structure the test if there is too many waits continuously.");
	}

	/**
	 * Try to find the locator until it is not stale on screen. This implies that the element exists on screen.
	 */
	public static void waitForElementNotStale(final By locator, final long timeout) {
		final FluentWait<WebDriver> wait =
				new FluentWait<WebDriver>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					WebElement element = driver.findElement(locator);
					element.isEnabled();
					return true;
				} catch (StaleElementReferenceException e) {
					LOGGER.info("Waiting. Element is stale: " + locator);
				} catch (NoSuchElementException e) {
					LOGGER.info("Waiting. Element does not exist: " + locator);
				}
				return false;
			}
		});
	}

	/**
	 * Wait until the attribute contains the expected value
	 */
	public static void waitForElementAttributeContains(final By locator, final long timeout, final String attribute,
			final String expectedContainedValue) {
		waitForElementAttributeContainsOneOf(locator, timeout, attribute, expectedContainedValue);
	}

	/**
	 * Wait until the attribute contains one of the expected values
	 */
	public static void waitForElementAttributeContainsOneOf(final By locator, final long timeout,
			final String attribute, final String... expectedContainedValues) {
		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(timeout, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				final String attributeValue = getDriver().findElement(locator).getAttribute(attribute);
				for (String expectedContainedValue : expectedContainedValues) {
					if (attributeValue.contains(expectedContainedValue)) {
						return true;
					}
				}
				return false;
			}
		});
	}

	private static boolean isElementInvisible(final By identifier) {
		try {
			List<WebElement> elements = getDriver().findElements(identifier);
			if (elements.isEmpty()) {
				return true;
			} else {
				String style = elements.get(0).getAttribute("style");
				return !elements.get(0).isDisplayed() || style.contains("visibility: hidden")
						|| style.contains("display: none");
			}
		} catch (Exception e) {
			LOGGER.info("Exception when checking mask." + e.getMessage());
			return false;
		}
	}

	public static WebElement waitForElementVisible(By locator) {
		return waitForElementVisible(locator, 60);
	}

	public static WebElement waitForElementVisible(By locator, long timeout) {
		return new WebDriverWait(getDriver(), timeout).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static void waitForElementInvisible(By locator, long timeout) {
		waitForElementNotExist(locator, timeout);
	}

	public static void waitForElementStoppedMoving(final By locator, long timeout) {
		new WebDriverWait(getDriver(), timeout).pollingEvery(1, TimeUnit.SECONDS)
				.until(new Function<WebDriver, Boolean>() {
					Point position;

					@Override
					public Boolean apply(WebDriver driver) {
						try {
							final WebElement element = driver.findElement(locator);
							if (position != null && element.getLocation().equals(position)) {
								return true;
							}
							position = element.getLocation();
							return false;
						} catch (StaleElementReferenceException se) {
							return false;
						}
					}
				});
	}

	public static void hoverElement(WebElement webElement) {
		new Actions(getDriver()).moveToElement(webElement).perform();
	}

	public static void hoverElement(By by) {
		hoverElement(getDriver().findElement(by));
	}

	public static void moveToAndClick(final By locator) {
		waitForElementExist(locator);

		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(60, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(final WebDriver driver) {
				try {
					waitForElementClickable(locator, 30);
					new Actions(getDriver()).moveToElement(findElement(locator)).sendKeys(Keys.ARROW_DOWN).click()
							.build().perform();
					return true;
				} catch (WebDriverException e) {
					return false;
				}
			}
		});
	}

	public static void moveElement(By by, int moveX, int moveY) {
		new Actions(getDriver()).dragAndDropBy(getDriver().findElement(by), moveX, moveY).perform();
	}

	public static void moveElement(WebElement webElement, int moveX, int moveY) {
		new Actions(getDriver()).dragAndDropBy(webElement, moveX, moveY).perform();
	}

	public static void focusElement(By by) {
		new Actions(getDriver()).moveToElement(getDriver().findElement(by)).perform();
	}

	public static void focusElement(WebElement webElement) {
		new Actions(getDriver()).moveToElement(webElement).perform();
	}

	public static void focusAndClickElement(WebElement webElement) {
		new Actions(getDriver()).moveToElement(webElement).click().perform();
	}

	public static void focusAndClickElement(By locator) {
		focusAndClickElement(waitAndGetElement(locator));
	}

	public static String getText(WebElement parent, By by) {
		List<WebElement> children = parent.findElements(by);
		return children.size() > 0 ? children.get(0).getText() : "";
	}

	public static void uploadFile(final String fileUploadInputId, final String filePath) {
		ExtendLog.action(String.format("Upload file [%s]", filePath));
		JSComponentHelper
				.executeJavaScript("document.getElementById('" + fileUploadInputId + "').style = 'display:block;';");
		findElement(By.id(fileUploadInputId)).sendKeys(filePath);
	}

	public static void dragAndDrop(WebElement dragElement, WebElement dropToElement) {
		Actions dragAndDropAction = new Actions(getDriver());
		dragAndDropAction.dragAndDrop(dragElement, dropToElement).build().perform();
	}

	public static void refresh() {
		getDriver().navigate().refresh();
	}

	/**
	 * This method allows empty list.
	 */
	public static List<String> getListText(final By selector) {
		List<WebElement> list = getDriver().findElements(selector);
		List<String> result = new ArrayList<String>();
		for (WebElement ele : list) {
			result.add(ele.getText());
		}
		return result;
	}

	public static List<String> getListText(final WebElement element, final By selector) {
		List<WebElement> list = element.findElements(selector);
		List<String> result = new ArrayList<String>();
		for (WebElement ele : list) {
			result.add(ele.getText());
		}
		return result;
	}

	public static void doubleClick(WebElement element) {
		final Actions action = new Actions(getDriver());
		action.doubleClick(element);
		action.perform();
	}

	public static void doubleClickByJS(final WebElement element) {
		// Try to use another way.
				element.click();
				element.click();
	}

	public static void doubleClick(By elementLocator) {
		final Actions action = new Actions(getDriver());
		action.doubleClick(findElement(elementLocator));
		action.perform();
	}

	public static void clearCookie() {
		ExtendLog.action("Clear cookie (instead of logging out)");
		getDriver().manage().deleteAllCookies();
	}

	public static boolean clickIfExist(By locator) {
		if (!findElements(locator).isEmpty()) {
			ExtendLog.action(String.format("Click on [%s]", locator.toString()));
			findElement(locator).click();
			return true;
		}
		return false;
	}

	public static String waitAndGetText(final By by, final long timeout) {
		return waitAndGetElement(by, timeout).getText();
	}

	public static String waitAndGetText(final By by) {
		return waitAndGetText(by, LoadingConstants.RENDER_ELEMENT);
	}

	public static void clearAndSetText(By locator, String text) {
		WebElement input = waitAndGetElement(locator);
		input.clear();
		input.sendKeys(text);
	}

	public static String getAttributeValue(final By locator, final String attributeName) {
		waitForElementExist(locator);
		return getDriver().findElement(locator).getAttribute(attributeName);
	}

	public static String waitAndGetAttributeValue(final By locator, final String attributeName) {
		ExtendLog.action(String.format("Wait for [%s]", locator.toString()));

		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(90, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(final WebDriver driver) {
				if (!driver.findElements(locator).isEmpty()) {
					return !"".equals(driver.findElements(locator).get(0).getAttribute(attributeName));
				}
				return false;
			}
		});

		return getDriver().findElement(locator).getAttribute(attributeName);
	}

	public static String getAttributeValue(final WebElement element, String attributeName) {
		return element.getAttribute(attributeName);
	}

	public static String getIdFromLocator(By locator) {
		return waitAndGetElement(locator).getAttribute("id");
	}

	public static void waitForURLContains(final String text) {
		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(getDriver())
				.withTimeout(LoadingConstants.LOADING_TIMEOUT, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(final WebDriver driver) {
				return driver.getCurrentUrl().contains(text);
			}
		});
	}

	public static void maximizeWindow() {
		getDriver().manage().window().maximize();
	}

	public static void tryToClickUntilDisappear(final By locator) {
		final FluentWait<WebDriver> wait =
				new FluentWait<WebDriver>(getDriver()).withTimeout(LoadingConstants.RENDER_ELEMENT, TimeUnit.SECONDS);
		wait.until(new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(final WebDriver driver) {
				if (!driver.findElements(locator).isEmpty()) {
					driver.findElement(locator).click();
				}
				return driver.findElements(locator).isEmpty();
			}
		});
	}

	/**
	 * Waiting explicitly some seconds is NEVER a good solution. Please check with development team to have mask on
	 * screen until it's available to work.
	 */
	@Deprecated
	public static void waitExplicitly(final long timeInSeconds) {
		try {
			Thread.sleep(timeInSeconds * 1000);
		} catch (InterruptedException e) {
			LOGGER.info("Try to wait in " + timeInSeconds + " but failed.", e);
		}
	}

	public static void switchToDefaultContent() {
		getDriver().switchTo().defaultContent();
	}

	public static void moveToAndSendKeys(final By locator, final int positionIndexFrom0, final String value) {
		WebElement element = WebElementHelper.waitAndGetElements(locator).get(positionIndexFrom0);
		new Actions(getDriver()).moveToElement(element).click().sendKeys(value).perform();
	}

	public static Boolean isElementExisting(final By locator) {
		return getDriver().findElements(locator).size() > 0;
	}

	/**
	 * This methods scrolls to element and from there scroll by an offset
	 *
	 * @param element
	 *            to scroll to
	 */
	public static void scrollToElement(WebElement element, int xoffset, int yoffset) {
		new Actions(getDriver()).moveToElement(element).perform();
		JSComponentHelper.executeJavaScript(String.format("window.scrollBy(%s,%s)", xoffset, yoffset));
	}
}
