package com.coursera.automation.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.coursera.automation.utils.ConfigReader;
import com.coursera.automation.utils.DriverManager;

public class BaseTest {

    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = DriverManager.initializeDriver();
    }

    @AfterClass
    public void tearDown() {
        DriverManager.quitDriver();
    }

    /**
     * Navigate to base URL
     */
    protected void navigateToBaseURL() {
        String baseURL = ConfigReader.getProperty("coursera.main.url");
        driver.get(baseURL);
    }
}
