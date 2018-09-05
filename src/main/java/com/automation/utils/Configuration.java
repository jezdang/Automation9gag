package com.automation.utils;

import com.automation.utils.DriverFactory.BrowserType;
import org.apache.log4j.Logger;

class Configuration {
    private static final Logger LOGGER = Logger.getLogger(Configuration.class);
    private static final String WINDOWS_OS = "Windows";
    private static final String LINUX_OS = "Linux";
    private static final String MAC_OS = "Mac";

    private static String detectOS() {
        String currentOS = System.getProperty("os.name").toLowerCase();
        ExtendLog.info("Current OS is: " + currentOS);
        if (currentOS.contains("win")) {
            return WINDOWS_OS;
        } else if(currentOS.contains("mac")) {
            return MAC_OS;
        } else if (currentOS.contains("nux") || currentOS.contains("nix")) {
            return LINUX_OS;
        } else {
            return "Other";
        }
    }

    private static String getWebDriverPath(String strCurrentOS, BrowserType type) {
        String driverPath = "";
        try {
            switch (type) {
                case FIREFOX:
                    if (WINDOWS_OS.contains(strCurrentOS)) {
                        driverPath = Constants.WEBDRIVER_GECKO_WIN32_PATH;
                    } else if (LINUX_OS.equals(strCurrentOS)) {
                        driverPath = Constants.WEBDRIVER_GECKO_LINUX64_PATH;
                    } else {
                        driverPath = "";
                    }
                    break;
                case CHROME:
                    if (WINDOWS_OS.contains(strCurrentOS)) {
                        driverPath = Constants.WEBDRIVER_CHROME_WIN32_PATH;
                    } else if (MAC_OS.equals(strCurrentOS)) {
                        driverPath = Constants.WEBDRIVER_CHROME_MAC_PATH;
                    } else if (LINUX_OS.equals(strCurrentOS)) {
                        driverPath = Constants.WEBDRIVER_CHROME_LINUX64_PATH;
                    } else {
                        driverPath = "";
                    }
                    break;
                case IE:
                    driverPath = Constants.WEBDRIVER_IE_PATH;
                    break;
                case EDGE:
                    driverPath = Constants.WEBDRIVER_EDGE_PATH;
                    break;
                case SAFARI:
                    driverPath = Constants.WEBDRIVER_SAFARI_PATH;
                    break;
                default:
                    throw new RuntimeException("Unsupported web browser: [" + type + "]");
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            LOGGER.error("No WebDriver Path found!");
        }
        LOGGER.info("WebDriverPath is: " + driverPath);
        return driverPath;
    }

    public static String getWebDriverPathWithCurrentOS(BrowserType browser) {
        return getWebDriverPath(detectOS(), browser);
    }
}
