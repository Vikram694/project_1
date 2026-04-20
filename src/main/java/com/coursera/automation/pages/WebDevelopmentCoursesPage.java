package com.coursera.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class WebDevelopmentCoursesPage extends BasePage {

    // Locators
    private static final By SEARCH_BOX = By.xpath("//input[@data-testid='search-autocomplete-input']");
    private static final By COURSE_CARDS = By.xpath("//div[@data-testid='product-card-cds']");
    private static final By COURSE_NAME = By.xpath(".//h3");
    private static final By COURSE_RATING = By.xpath(".//span[contains(@class,'css-4s48ix') or contains(@class, 'cds-119')]");
    private static final By COURSE_DURATION = By.xpath(".//div[contains(@class,'cds-CommonCard-metadata')]");
    private static final By DIFFICULTY_FILTER = By.xpath("//button[@data-testid='filter-dropdown-productDifficultyLevel']");
    private static final By LANGUAGE_FILTER = By.xpath("//button[@data-testid='filter-dropdown-language']");
    private static final String DIFFICULTY_OPTION_TEMPLATE = "//div[@data-testid='productDifficultyLevel:%s-false']//label";
    private static final String LANGUAGE_OPTION_TEMPLATE = "//div[@data-testid='language:%s-false']//label";
    private static final By FILTER_VIEW_BUTTON = By.xpath("//button[@data-testid='filter-view-button']");

    public WebDevelopmentCoursesPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Search for web development courses
     * @param keyword Search keyword
     */
    public void searchCourses(String keyword) {
        WebElement searchBox = waitForElementToBeClickable(SEARCH_BOX);
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.ENTER);
    }

    /**
     * Apply difficulty filter
     * @param difficulty Difficulty level (e.g., "Beginner")
     */
    public void applyDifficultyFilter(String difficulty) {
        applyGeneralFilter(DIFFICULTY_FILTER, String.format(DIFFICULTY_OPTION_TEMPLATE, difficulty));
    }

    /**
     * Apply language filter
     * @param language Language (e.g., "English")
     */
    public void applyLanguageFilter(String language) {
        applyGeneralFilter(LANGUAGE_FILTER, String.format(LANGUAGE_OPTION_TEMPLATE, language));
    }

    /**
     * Generic filter application method
     * @param filterButton Filter button locator
     * @param optionTemplate Option template locator
     */
    private void applyGeneralFilter(By filterButton, String optionTemplate) {
        try {
            clickElementWithJS(filterButton);
            
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(optionTemplate)));
            clickElementWithJS(option);
            
            WebElement viewButton = wait.until(ExpectedConditions.elementToBeClickable(FILTER_VIEW_BUTTON));
            clickElementWithJS(viewButton);
            
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(optionTemplate)));
        } catch (Exception e) {
            System.out.println("Filter application failed: " + e.getMessage());
        }
    }

    /**
     * Get all course cards
     * @return List of course card elements
     */
    public List<WebElement> getCourseCards() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(COURSE_CARDS));
    }

    /**
     * Get course name from card
     * @param card Course card element
     * @return Course name
     */
    public String getCourseName(WebElement card) {
        return card.findElement(COURSE_NAME).getText();
    }

    /**
     * Get course rating from card
     * @param card Course card element
     * @return Course rating or "Not found"
     */
    public String getCourseRating(WebElement card) {
        try {
            return card.findElement(COURSE_RATING).getText();
        } catch (Exception e) {
            return "Not found";
        }
    }

    /**
     * Get course duration from card
     * @param card Course card element
     * @return Course duration
     */
    public String getCourseDuration(WebElement card) {
        return card.findElement(COURSE_DURATION).getText();
    }
}
