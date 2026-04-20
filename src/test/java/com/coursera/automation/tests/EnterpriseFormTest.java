package com.coursera.automation.tests;

import com.coursera.automation.pages.EnterpriseFormPage;
import com.coursera.automation.utils.ConfigReader;
import org.testng.annotations.Test;

public class EnterpriseFormTest extends BaseTest {

    @Test(description = "Test Enterprise Form Validation with Invalid Email", groups = "enterprise", dependsOnGroups = "language")
    public void testEnterpriseFormValidation() {
        navigateToBaseURL();
        
        EnterpriseFormPage enterpriseForm = new EnterpriseFormPage(driver);
        
        // Get test data from properties
        String firstName = ConfigReader.getProperty("firstName");
        String lastName = ConfigReader.getProperty("lastName");
        String invalidEmail = ConfigReader.getProperty("invalidEmail");
        
        System.out.println("\n--- 3. ENTERPRISE FORM VALIDATION ---");
        
        // Submit form with invalid email
        String errorMessage = enterpriseForm.submitEnterpriseForm(firstName, lastName, invalidEmail);
        
        System.out.println("TERMINAL OUTPUT - ERROR MESSAGE:");
        System.out.println("******************************************");
        System.out.println(errorMessage);
        System.out.println("******************************************");
    }
}
