package com.coursera.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
    }

    /**
     * Click element using JavaScript
     * @param locator By locator
     */
    protected void clickElementWithJS(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * Click element using JavaScript with provided element
     * @param element WebElement to click
     */
    protected void clickElementWithJS(WebElement element) {
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * Send keys to element with explicit wait
     * @param locator By locator
     * @param keys Keys to send
     */
    protected void sendKeys(By locator, String keys) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.sendKeys(keys);
    }

    /**
     * Wait for element to be clickable
     * @param locator By locator
     * @return WebElement
     */
    protected WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for element to be visible
     * @param locator By locator
     * @return WebElement
     */
    protected WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for all elements to be present
     * @param locator By locator
     * @return List of WebElements
     */
    protected java.util.List<WebElement> waitForAllElementsToBePresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    /**
     * Wait for element to be present
     * @param locator By locator
     * @return WebElement
     */
    protected WebElement waitForElementToBePresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for element invisibility
     * @param locator By locator
     */
    protected void waitForElementToBeInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Scroll page to bottom
     */
    protected void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    /**
     * Scroll page by pixels
     * @param pixels Number of pixels to scroll
     */
    protected void scrollByPixels(int pixels) {
        js.executeScript("window.scrollBy(0, " + pixels + ");");
    }

    /**
     * Navigate to URL
     * @param url URL to navigate
     */
    protected void navigateTo(String url) {
        driver.get(url);
    }

    /**
     * Get text of element
     * @param locator By locator
     * @return Text content
     */
    protected String getText(By locator) {
        return waitForElementToBeVisible(locator).getText();
    }
}
