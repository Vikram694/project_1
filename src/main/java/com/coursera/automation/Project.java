package com.coursera.automation;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Project {

    public static void main(String[] args) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        // Using a strong 20-second explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // ================= 1. WEB DEVELOPMENT COURSES =================
            System.out.println("--- 1. WEB DEVELOPMENT COURSES ---");
            driver.get("https://www.coursera.org");
            
            WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-testid='search-autocomplete-input']")));
            searchBox.sendKeys("web development");
            searchBox.sendKeys(Keys.ENTER);

            // Apply initial filters
            applyGeneralFilter(driver, wait, "filter-dropdown-productDifficultyLevel", "productDifficultyLevel:Beginner-false");
            applyGeneralFilter(driver, wait, "filter-dropdown-language", "language:English-false");

            List<WebElement> courses = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@data-testid='product-card-cds']")));
            for (int i = 0; i < Math.min(2, courses.size()); i++) {
                WebElement card = courses.get(i);
                System.out.println("Course Name: " + card.findElement(By.xpath(".//h3")).getText());
                try {
                    System.out.println("Rating     : " + card.findElement(By.xpath(".//span[contains(@class,'css-4s48ix') or contains(@class, 'cds-119')]")).getText());
                } catch (Exception e) { System.out.println("Rating     : Not found"); }
                System.out.println("Duration   : " + card.findElement(By.xpath(".//div[contains(@class,'cds-CommonCard-metadata')]")).getText());
                System.out.println("--------------------------------------------------");
            }

            // ================= 2. LANGUAGE LEARNING (EXPLICIT WAITS ONLY) =================
            System.out.println("\n--- 2. LANGUAGE & LEVEL EXTRACTION ---");
            driver.get("https://www.coursera.org");
            
            WebElement langSearch = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-testid='search-autocomplete-input']")));
            langSearch.sendKeys("Language Learning" + Keys.ENTER);

            // Step A: Open Language Dropdown to Extract Names
            clickElementWithJS(driver, wait, By.xpath("//button[@data-testid='filter-dropdown-language']"));
            
            List<WebElement> langElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[starts-with(@data-testid, 'language:')]")));
            
            Set<String> languageSet = new LinkedHashSet<>();
            for (int i = 0; i < Math.min(5, langElements.size()); i++) {
                String exactLangName = langElements.get(i).getAttribute("data-testid").replace("language:", "").replace("-false", "");
                languageSet.add(exactLangName);
            }

            // Close Language Dropdown by clicking View and waiting for invisibility
            WebElement initViewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid='filter-view-button']")));
            js.executeScript("arguments[0].click();", initViewBtn);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[starts-with(@data-testid, 'language:')]")));

            // Step B: Iterate through the LinkedHashSet
            for (String langName : languageSet) {
                System.out.println("\n[Processing Language: " + langName + "]");

                // 1. Wait for and Open Language Dropdown
                WebElement langBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid='filter-dropdown-language']")));
                js.executeScript("arguments[0].click();", langBtn);

                // 2. Click "Clear All" if it exists
                try {
                    WebElement clearBtn = driver.findElement(By.xpath("//button[.//span[text()='Clear All']]"));
                    if (clearBtn.isEnabled() && clearBtn.getAttribute("disabled") == null) {
                        js.executeScript("arguments[0].click();", clearBtn);
                    }
                } catch (Exception e) { /* Ignored if disabled or not present */ }

                // 3. Select the exact language using the data-testid
                String exactLocator = "//div[@data-testid='language:" + langName + "-false']//label";
                WebElement langCheckbox = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(exactLocator)));
                js.executeScript("arguments[0].click();", langCheckbox);

                // Setup Staleness Check: Find existing cards before clicking view
                List<WebElement> oldCards = driver.findElements(By.xpath("//div[@data-testid='product-card-cds']"));

                // 4. Click View to apply Language
                WebElement viewBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid='filter-view-button']")));
                js.executeScript("arguments[0].click();", viewBtn);
                
                // 5. EXPLICIT WAIT: Wait for old search results to vanish and new ones to appear
                if (!oldCards.isEmpty()) {
                    try {
                        wait.until(ExpectedConditions.stalenessOf(oldCards.get(0)));
                    } catch (Exception e) { /* Ignore if it updates too fast */ }
                }
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-testid='product-card-cds']")));

                // 6. Open Level Dropdown
                WebElement levelBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid='filter-dropdown-productDifficultyLevel']")));
                js.executeScript("arguments[0].click();", levelBtn);

                // 7. Retrieve Levels
                try {
                    List<WebElement> levelResults = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                        By.xpath("//div[starts-with(@data-testid, 'productDifficultyLevel:')]//div[contains(@class,'cds-checkboxAndRadio-labelText')]")));
                    
                    if (levelResults.isEmpty()) {
                        System.out.println("  - No levels available.");
                    } else {
                        for (WebElement lvl : levelResults) {
                            System.out.println("  - " + lvl.getText().replace("\n", " "));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("  - No levels found.");
                }

                // 8. CLOSE LEVEL DROPDOWN: Click the 'View' button inside the popover to collapse it
                WebElement closeLevelBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid='filter-view-button']")));
                js.executeScript("arguments[0].click();", closeLevelBtn);

                // 9. EXPLICIT WAIT: DO NOT loop again until the Level dropdown is physically gone from the screen
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[starts-with(@data-testid, 'productDifficultyLevel:')]")));
            }

            // ================= 3. ENTERPRISE FORM VALIDATION =================
            System.out.println("\n--- 3. ENTERPRISE FORM VALIDATION ---");
            driver.get("https://www.coursera.org");
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            WebElement entLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("For Enterprise")));
            js.executeScript("arguments[0].click();", entLink);

            WebElement campusLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("For Campus")));
            js.executeScript("arguments[0].click();", campusLink);

            js.executeScript("window.scrollBy(0, 1000);");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("FirstName"))).sendKeys("TestUser");
            driver.findElement(By.id("LastName")).sendKeys("TestLast");
            driver.findElement(By.id("Email")).sendKeys("invalid-email-format");

            WebElement submitBtn = driver.findElement(By.xpath("//button[@type='submit' and contains(@class, 'mktoButton')]"));
            js.executeScript("arguments[0].click();", submitBtn);

            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mktoErrorMsg")));
            System.out.println("TERMINAL OUTPUT - ERROR MESSAGE:");
            System.out.println("******************************************");
            System.out.println(errorMsg.getText());
            System.out.println("******************************************");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    // Helper to apply filters using explicit waits for the popovers to close
    private static void applyGeneralFilter(WebDriver driver, WebDriverWait wait, String dropdownTestId, String optionTestId) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            clickElementWithJS(driver, wait, By.xpath("//button[@data-testid='" + dropdownTestId + "']"));
            
            WebElement opt = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-testid='" + optionTestId + "']//label")));
            js.executeScript("arguments[0].click();", opt);
            
            WebElement view = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-testid='filter-view-button']")));
            js.executeScript("arguments[0].click();", view);
            
            // Wait for dropdown to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@data-testid='" + optionTestId + "']")));
        } catch (Exception e) {
            System.out.println("Filter setup failed for: " + optionTestId);
        }
    }

    private static void clickElementWithJS(WebDriver driver, WebDriverWait wait, By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
