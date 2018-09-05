package com.automation.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.util.concurrent.TimeUnit;

public class DriverFactory {
    private static final Logger LOGGER = Logger.getLogger(DriverFactory.class);

    public enum BrowserType {FIREFOX, CHROME, IE, EDGE, ANDROID, IOS, SAFARI}

    public static WebDriver driver;

    public static BrowserType getBrowserType() {
        String desiredBrowserName = System.getProperty("browser", "chrome");
        return BrowserType.valueOf(StringUtils.upperCase(desiredBrowserName));
    }

    public static WebDriver getDriver() {
        try {
            if (null == driver) {
                BrowserType browserType = getBrowserType();
                String driverPath = Configuration.getWebDriverPathWithCurrentOS(browserType);
                LOGGER.info("Initiate browser: " + browserType);
                switch (browserType) {
                    case FIREFOX:
                        System.setProperty("webdriver.gecko.driver", driverPath);
                        FirefoxProfile profile = new FirefoxProfile();
                        profile.setAcceptUntrustedCertificates(true);
                        final FirefoxOptions firefoxOptions = new FirefoxOptions();
                        firefoxOptions.setProfile(profile).setCapability("marionette", true);
                        driver = new FirefoxDriver(firefoxOptions);
                        break;

                    case CHROME:
                        System.setProperty("webdriver.chrome.driver", driverPath);
                        ChromeOptions options = new ChromeOptions();
                        options.addArguments("start-maximized");
                        driver = new ChromeDriver(options);
                        break;

                    case IE:
                        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
                        System.setProperty("webdriver.ie.driver", Constants.WEBDRIVER_IE_PATH);
                        capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, Constants.URL_HOMEPAGE_IE);
                        capabilities.setCapability("ignoreZoomSetting", true);
                        capabilities.setCapability("nativeEvents", false);
                        driver = new InternetExplorerDriver(capabilities);
                        break;

                    case SAFARI:
                        System.setProperty("webdriver.safari.driver", driverPath);
                        SafariOptions safariOptions = new SafariOptions();
                        safariOptions.setUseCleanSession(true);
                        safariOptions.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
                        driver = new SafariDriver(safariOptions);
                        break;

                    case EDGE:
                        System.setProperty("webdriver.edge.driver", driverPath);
                        EdgeOptions edgeOptions = new EdgeOptions();
                        edgeOptions.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);
                        edgeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                        edgeOptions.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
                        driver = new EdgeDriver(edgeOptions);
                        break;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Unable to load browser: " + e.getMessage());
        } finally {
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(Constants.LOADING_TIMEOUT, TimeUnit.SECONDS);
        }
        return driver;
    }
}
