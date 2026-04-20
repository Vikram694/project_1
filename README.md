# Coursera Automation Project

This is a Selenium automation project for Coursera using **Page Object Model (POM)** design pattern, **Maven**, and **TestNG** framework.

## Project Structure

```
coursera-automation-project/
├── pom.xml                                    # Maven configuration file
├── testng.xml                                 # TestNG configuration
├── README.md                                  # Project documentation
│
├── src/
│   ├── main/
│   │   ├── java/com/coursera/automation/
│   │   │   ├── Project.java                   # Original project code (unchanged)
│   │   │   ├── pages/
│   │   │   │   ├── BasePage.java              # Base page class with common methods
│   │   │   │   ├── WebDevelopmentCoursesPage.java  # Web dev courses page object
│   │   │   │   ├── LanguageLearningPage.java  # Language learning page object
│   │   │   │   └── EnterpriseFormPage.java    # Enterprise form page object
│   │   │   └── utils/
│   │   │       ├── ConfigReader.java          # Properties file reader
│   │   │       └── DriverManager.java         # WebDriver management
│   │   │
│   │   └── resources/
│   │       └── config.properties              # Configuration and test data
│   │
│   └── test/
│       └── java/com/coursera/automation/tests/
│           ├── BaseTest.java                  # Base test class
│           ├── WebDevelopmentCoursesTest.java # Web dev courses test
│           ├── LanguageLearningTest.java      # Language learning test
│           └── EnterpriseFormTest.java        # Enterprise form test
```

## Features

✅ **Page Object Model (POM)** - Organized page objects for maintainability
✅ **Data-Driven Testing** - Test data in `config.properties`
✅ **Maven Build** - Easy dependency management
✅ **TestNG Framework** - Powerful test execution and reporting
✅ **Explicit Waits** - Robust element waiting strategies
✅ **JavaScript Execution** - Handles interactive elements
✅ **Original Code Preserved** - The original Project.java is kept unchanged

## Configuration (config.properties)

The `src/main/resources/config.properties` file contains:

```properties
# Browser Configuration
browser=chrome
baseURL=https://www.coursera.org
chrome.start.maximized=true
chrome.disable.notifications=true
chrome.remote.allow.origins=true

# Timeouts
explicit.wait.timeout=20
implicit.wait.timeout=10

# Test Data
firstName=TestUser
lastName=TestLast
invalidEmail=invalid-email-format

# Search and Filter Settings
course.difficulty=Beginner
course.language=English
course.search.keyword=web development
course.limit=2
language.search.keyword=Language Learning
language.limit=5
```

## Prerequisites

- **Java 11+** installed
- **Maven 3.6+** installed
- **Chrome browser** installed
- **ChromeDriver** (automatically managed by WebDriverManager)

## Setup Instructions

### 1. Clone or Extract the Project
```bash
cd coursera-automation-project
```

### 2. Install Dependencies
```bash
mvn clean install
```

### 3. Update config.properties
Edit `src/main/resources/config.properties` with your test data:
```properties
firstName=Your First Name
lastName=Your Last Name
invalidEmail=invalid@test.com
```

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=WebDevelopmentCoursesTest
mvn test -Dtest=LanguageLearningTest
mvn test -Dtest=EnterpriseFormTest
```

### Run Using TestNG XML
```bash
mvn test -Dsuite=testng.xml
```

### Run Original Project Class (main method)
```bash
mvn exec:java -Dexec.mainClass="com.coursera.automation.Project"
```

## Page Objects

### WebDevelopmentCoursesPage
- `searchCourses(String keyword)` - Search for courses
- `applyDifficultyFilter(String difficulty)` - Apply difficulty filter
- `applyLanguageFilter(String language)` - Apply language filter
- `getCourseCards()` - Get all course card elements
- `getCourseName(WebElement card)` - Extract course name
- `getCourseRating(WebElement card)` - Extract course rating
- `getCourseDuration(WebElement card)` - Extract course duration

### LanguageLearningPage
- `searchLanguageLearning(String keyword)` - Search for language courses
- `extractLanguages(int limit)` - Extract available languages
- `selectLanguage(String langName)` - Select specific language
- `getAvailableLevels()` - Get available proficiency levels
- `closeLevelDropdown()` - Close level filter dropdown

### EnterpriseFormPage
- `navigateToForEnterprise()` - Navigate to enterprise section
- `navigateToForCampus()` - Navigate to campus section
- `fillEnterpriseForm(String firstName, String lastName, String email)` - Fill form
- `submitForm()` - Submit the form
- `getErrorMessage()` - Get validation error message
- `submitEnterpriseForm(...)` - Complete form submission workflow

## Utilities

### ConfigReader
Reads properties from `config.properties` file:
- `getProperty(String key)` - Get string property
- `getProperty(String key, String defaultValue)` - Get with default
- `getIntProperty(String key)` - Get as integer
- `getBooleanProperty(String key)` - Get as boolean

### DriverManager
Manages WebDriver lifecycle:
- `initializeDriver()` - Initialize WebDriver
- `getDriver()` - Get current driver instance
- `quitDriver()` - Quit and cleanup

## Test Reports

After running tests, reports are generated in:
```
target/surefire-reports/
```

## Dependencies

- Selenium WebDriver 4.15.0
- TestNG 7.8.1
- WebDriverManager 5.6.2
- Log4j 1.2.17

## Original Code

The original `Project.java` is preserved in:
```
src/main/java/com/coursera/automation/Project.java
```

The code remains **exactly the same** with no modifications to maintain backward compatibility.

## Troubleshooting

### Chrome not found
- Install Google Chrome
- Or update `config.properties` to use a different browser

### WebDriver not found
- WebDriverManager will download the correct driver automatically
- Ensure internet connection is available

### Properties file not found
- Ensure `config.properties` is in `src/main/resources/`
- Check file encoding is UTF-8

### Timeouts during execution
- Increase `explicit.wait.timeout` in `config.properties`
- Ensure stable internet connection
- Check if Coursera website is accessible

## License

This project is provided as-is for automation testing purposes.

## Support

For issues or questions, please review:
1. The Page Object classes
2. The TestNG configuration (testng.xml)
3. The config.properties file
4. The original Project.java for reference logic
