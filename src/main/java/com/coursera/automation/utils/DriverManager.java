package com.coursera.automation.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {

    private static WebDriver driver;

    /**
     * Initialize WebDriver based on browser type from config
     * @return WebDriver instance
     */
    public static WebDriver initializeDriver() {
        String browser = ConfigReader.getProperty("browser", "chrome").toLowerCase();
        
        if (browser.equals("chrome")) {
            driver = initializeChromeDriver();
        } else {
            throw new RuntimeException("Browser: " + browser + " is not supported");
        }
        
        return driver;
    }

    /**
     * Initialize Chrome WebDriver with options
     * @return Chrome WebDriver instance
     */
    private static WebDriver initializeChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        
        if (ConfigReader.getBooleanProperty("chrome.start.maximized")) {
            options.addArguments("--start-maximized");
        }
        
        if (ConfigReader.getBooleanProperty("chrome.disable.notifications")) {
            options.addArguments("--disable-notifications");
        }
        
        if (ConfigReader.getBooleanProperty("chrome.remote.allow.origins")) {
            options.addArguments("--remote-allow-origins=*");
        }
        
        driver = new ChromeDriver(options);
        return driver;
    }

    /**
     * Get the current WebDriver instance
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {
        return driver;
    }

    /**
     * Quit and close the WebDriver
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
