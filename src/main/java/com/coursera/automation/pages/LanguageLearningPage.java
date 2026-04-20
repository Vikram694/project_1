package com.coursera.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class LanguageLearningPage extends BasePage {

    // Locators
    private static final By SEARCH_BOX = By.xpath("//input[@data-testid='search-autocomplete-input']");
    private static final By LANGUAGE_FILTER_BUTTON = By.xpath("//button[@data-testid='filter-dropdown-language']");
    private static final By LANGUAGE_OPTIONS = By.xpath("//div[starts-with(@data-testid, 'language:')]");
    private static final By LANGUAGE_LABELS = By.xpath("//div[starts-with(@data-testid, 'language:')]//label");
    private static final By LEVEL_FILTER_BUTTON = By.xpath("//button[@data-testid='filter-dropdown-productDifficultyLevel']");
    private static final By LEVEL_OPTIONS = By.xpath("//div[starts-with(@data-testid, 'productDifficultyLevel:')]");
    private static final By LEVEL_LABELS = By.xpath("//div[starts-with(@data-testid, 'productDifficultyLevel:')]//div[contains(@class,'cds-checkboxAndRadio-labelText')]");
    private static final By FILTER_VIEW_BUTTON = By.xpath("//button[@data-testid='filter-view-button']");
    private static final By CLEAR_ALL_BUTTON = By.xpath("//button[.//span[text()='Clear All']]");
    private static final By COURSE_CARDS = By.xpath("//div[@data-testid='product-card-cds']");

    public LanguageLearningPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Search for language learning courses
     * @param keyword Search keyword
     */
    public void searchLanguageLearning(String keyword) {
        WebElement searchBox = waitForElementToBeClickable(SEARCH_BOX);
        searchBox.sendKeys(keyword);
        searchBox.sendKeys(Keys.ENTER);
    }

    /**
     * Extract available languages
     * @param limit Maximum number of languages to extract
     * @return Set of language names
     */
    public Set<String> extractLanguages(int limit) {
        clickElementWithJS(LANGUAGE_FILTER_BUTTON);
        
        List<WebElement> langElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(LANGUAGE_OPTIONS));
        
        Set<String> languageSet = new LinkedHashSet<>();
        for (int i = 0; i < Math.min(limit, langElements.size()); i++) {
            String exactLangName = langElements.get(i).getAttribute("data-testid")
                    .replace("language:", "").replace("-false", "");
            languageSet.add(exactLangName);
        }
        
        WebElement viewBtn = wait.until(ExpectedConditions.elementToBeClickable(FILTER_VIEW_BUTTON));
        clickElementWithJS(viewBtn);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(LANGUAGE_OPTIONS));
        
        return languageSet;
    }

    /**
     * Select a specific language
     * @param langName Language name
     */
    public void selectLanguage(String langName) {
        clickElementWithJS(LANGUAGE_FILTER_BUTTON);
        
        try {
            WebElement clearBtn = driver.findElement(CLEAR_ALL_BUTTON);
            if (clearBtn.isEnabled() && clearBtn.getAttribute("disabled") == null) {
                clickElementWithJS(clearBtn);
            }
        } catch (Exception e) {
            // Ignored if disabled or not present
        }
        
        String exactLocator = "//div[@data-testid='language:" + langName + "-false']//label";
        WebElement langCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(exactLocator)));
        clickElementWithJS(langCheckbox);
        
        List<WebElement> oldCards = driver.findElements(COURSE_CARDS);
        
        WebElement viewBtn = wait.until(ExpectedConditions.elementToBeClickable(FILTER_VIEW_BUTTON));
        clickElementWithJS(viewBtn);
        
        if (!oldCards.isEmpty()) {
            try {
                wait.until(ExpectedConditions.stalenessOf(oldCards.get(0)));
            } catch (Exception e) {
                // Ignore if it updates too fast
            }
        }
        
        wait.until(ExpectedConditions.presenceOfElementLocated(COURSE_CARDS));
    }

    /**
     * Get available levels for a language
     * @return List of level names
     */
    public List<String> getAvailableLevels() {
        WebElement levelBtn = wait.until(ExpectedConditions.elementToBeClickable(LEVEL_FILTER_BUTTON));
        clickElementWithJS(levelBtn);
        
        try {
            List<WebElement> levelResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(LEVEL_LABELS));
            
            java.util.List<String> levels = new java.util.ArrayList<>();
            for (WebElement lvl : levelResults) {
                levels.add(lvl.getText().replace("\n", " "));
            }
            return levels;
        } catch (Exception e) {
            return new java.util.ArrayList<>();
        }
    }

    /**
     * Close level dropdown
     */
    public void closeLevelDropdown() {
        WebElement closeLevelBtn = wait.until(ExpectedConditions.elementToBeClickable(FILTER_VIEW_BUTTON));
        clickElementWithJS(closeLevelBtn);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(LEVEL_OPTIONS));
    }
}
