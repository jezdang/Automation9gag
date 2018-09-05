package com.automation.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SharedDriver extends EventFiringWebDriver {
    private static final Logger LOGGER = Logger.getLogger(SharedDriver.class);

    public static WebDriver real_driver;

    private static final Thread close_thread = new Thread() {
        @Override
        public void run() {
            real_driver.quit();
        }
    };

    static {
        Runtime.getRuntime().addShutdownHook(close_thread);
        try {
            real_driver = DriverFactory.getDriver();
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
            LOGGER.info("########## Start new shared driver [" + timeStamp + "] ##########");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new Error(throwable);
        }
    }

    public SharedDriver() {
        super(real_driver);
        real_driver.manage().timeouts().implicitlyWait(Constants.LOADING_TIMEOUT, TimeUnit.SECONDS);
    }

    @Override
    public void quit() {
        if (Thread.currentThread() != close_thread) {
            throw new UnsupportedOperationException("You shouldn't quit this WebDriver. It's shared and will quit when the JVM exits.");
        }
        super.quit();
    }


}
