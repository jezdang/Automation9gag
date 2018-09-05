package com.automation.utils;

public class Constants {
    // WebDriver Path
    public static String WEBDRIVER_GECKO_WIN32_PATH = "src/main/resources/bin/geckodriver32.exe";
    public static String WEBDRIVER_GECKO_LINUX32_PATH = "/src/main/resources/bin/geckodriverlinux32";
    public static String WEBDRIVER_GECKO_LINUX64_PATH =  "/src/main/resources/bin/geckodriverlinux64";
    public static String WEBDRIVER_CHROME_WIN32_PATH = "/src/main/resources/bin/chromedriver32.exe";
    public static String WEBDRIVER_CHROME_LINUX32_PATH = "/src/main/resources/bin/chromedriverlinux32";
    public static String WEBDRIVER_CHROME_LINUX64_PATH = "/src/main/resources/bin/chromedriverlinux64";
    public static String WEBDRIVER_EDGE_PATH = "/src/main/resources/bin/MicrosoftWebDriver.exe";
    public static String WEBDRIVER_IE_PATH = "/src/main/resources/bin/IEDriverServer.exe";
    public static String WEBDRIVER_SAFARI_PATH = "usr/bin/safaridriver";
    public static String WEBDRIVER_CHROME_MAC_PATH = "src/main/resources/bin/chromedriver";

    // Set Loading timeout
    public static final long LOADING_TIMEOUT = 120l;

    // Set URL
    public static final String URL_HOMEPAGE_IE = "http://www.bing.com/";
    public static final String URL_HOMEPAGE_9GAG = "https://9gag.com/";

    // Test data
    public static final String EMAIL_LOGIN = "jez.dang@gmail.com";
    public static final String PASSWORD_LOGIN = "22091989";

    private Constants() {
        // do nothing
    }
}
