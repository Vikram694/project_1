package com.coursera.automation.tests;

import com.coursera.automation.pages.WebDevelopmentCoursesPage;
import com.coursera.automation.utils.ConfigReader;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import java.util.List;

public class WebDevelopmentCoursesTest extends BaseTest {

    @Test(description = "Test Web Development Courses Search and Filter", groups = "web")
    public void testWebDevelopmentCourses() {
        navigateToBaseURL();
        
        WebDevelopmentCoursesPage coursesPage = new WebDevelopmentCoursesPage(driver);
        
        // Search for courses
        String searchKeyword = ConfigReader.getProperty("course.search.keyword");
        coursesPage.searchCourses(searchKeyword);
        
        // Apply filters
        String difficulty = ConfigReader.getProperty("course.difficulty");
        String language = ConfigReader.getProperty("course.language");
        coursesPage.applyDifficultyFilter(difficulty);
        coursesPage.applyLanguageFilter(language);
        
        // Get and display courses
        int courseLimit = ConfigReader.getIntProperty("course.limit");
        List<WebElement> courses = coursesPage.getCourseCards();
        
        System.out.println("--- 1. WEB DEVELOPMENT COURSES ---");
        for (int i = 0; i < Math.min(courseLimit, courses.size()); i++) {
            WebElement card = courses.get(i);
            System.out.println("Course Name: " + coursesPage.getCourseName(card));
            System.out.println("Rating     : " + coursesPage.getCourseRating(card));
            System.out.println("Duration   : " + coursesPage.getCourseDuration(card));
            System.out.println("--------------------------------------------------");
        }
    }
}
