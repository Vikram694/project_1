package com.coursera.automation.tests;

import com.coursera.automation.pages.LanguageLearningPage;
import com.coursera.automation.utils.ConfigReader;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Set;

public class LanguageLearningTest extends BaseTest {

    @Test(description = "Test Language Learning Courses and Level Extraction", groups = "language", dependsOnGroups = "web")
    public void testLanguageLearningAndLevels() {
        navigateToBaseURL();
        
        LanguageLearningPage languagePage = new LanguageLearningPage(driver);
        
        // Search for language learning courses
        String searchKeyword = ConfigReader.getProperty("language.search.keyword");
        languagePage.searchLanguageLearning(searchKeyword);
        
        // Extract available languages
        int languageLimit = ConfigReader.getIntProperty("language.limit");
        Set<String> languages = languagePage.extractLanguages(languageLimit);
        
        System.out.println("\n--- 2. LANGUAGE & LEVEL EXTRACTION ---");
        
        // Process each language
        for (String langName : languages) {
            System.out.println("\n[Processing Language: " + langName + "]");
            
            // Select the language
            languagePage.selectLanguage(langName);
            
            // Get available levels for this language
            List<String> levels = languagePage.getAvailableLevels();
            
            if (levels.isEmpty()) {
                System.out.println("  - No levels available.");
            } else {
                for (String level : levels) {
                    System.out.println("  - " + level);
                }
            }
            
            // Close the level dropdown
            languagePage.closeLevelDropdown();
        }
    }
}
