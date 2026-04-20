package com.coursera.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class EnterpriseFormPage extends BasePage {

    // Locators
    private static final By FOR_ENTERPRISE_LINK = By.linkText("For Enterprise");
    private static final By FOR_CAMPUS_LINK = By.linkText("For Campus");
    private static final By FIRST_NAME_FIELD = By.id("FirstName");
    private static final By LAST_NAME_FIELD = By.id("LastName");
    private static final By EMAIL_FIELD = By.id("Email");
    private static final By SUBMIT_BUTTON = By.xpath("//button[@type='submit' and contains(@class, 'mktoButton')]");
    private static final By ERROR_MESSAGE = By.className("mktoErrorMsg");

    public EnterpriseFormPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Navigate to For Enterprise page
     */
    public void navigateToForEnterprise() {
        scrollToBottom();
        WebElement entLink = wait.until(ExpectedConditions.elementToBeClickable(FOR_ENTERPRISE_LINK));
        clickElementWithJS(entLink);
    }

    /**
     * Navigate to For Campus page
     */
    public void navigateToForCampus() {
        WebElement campusLink = wait.until(ExpectedConditions.elementToBeClickable(FOR_CAMPUS_LINK));
        clickElementWithJS(campusLink);
    }

    /**
     * Fill enterprise form with test data
     * @param firstName First name
     * @param lastName Last name
     * @param email Email address
     */
    public void fillEnterpriseForm(String firstName, String lastName, String email) {
        scrollByPixels(1000);
        
        WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME_FIELD));
        firstNameInput.sendKeys(firstName);
        
        driver.findElement(LAST_NAME_FIELD).sendKeys(lastName);
        driver.findElement(EMAIL_FIELD).sendKeys(email);
    }

    /**
     * Submit the form
     */
    public void submitForm() {
        WebElement submitBtn = driver.findElement(SUBMIT_BUTTON);
        clickElementWithJS(submitBtn);
    }

    /**
     * Get error message
     * @return Error message text
     */
    public String getErrorMessage() {
        WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(ERROR_MESSAGE));
        return errorMsg.getText();
    }

    /**
     * Complete enterprise form submission (for test purposes)
     * @param firstName First name
     * @param lastName Last name
     * @param email Email address
     * @return Error message if validation fails
     */
    public String submitEnterpriseForm(String firstName, String lastName, String email) {
        navigateToForEnterprise();
        navigateToForCampus();
        fillEnterpriseForm(firstName, lastName, email);
        submitForm();
        return getErrorMessage();
    }
}
