# Quick Start Guide

## Installation & Setup

### Step 1: Extract the Project
```bash
unzip coursera-automation-project.zip
cd coursera-automation-project
```

### Step 2: Build the Project
```bash
mvn clean install
```

This will:
- Download all dependencies
- Compile the source code
- Create the target folder

### Step 3: Update Test Data (Optional)
Edit `src/main/resources/config.properties`:
```properties
# Change these values if needed
firstName=YourFirstName
lastName=YourLastName
invalidEmail=youremail@test.com
```

## Running Tests

### Option 1: Run All Tests via TestNG
```bash
mvn test
```

### Option 2: Run Specific Test
```bash
# Run only web development courses test
mvn test -Dtest=WebDevelopmentCoursesTest

# Run only language learning test
mvn test -Dtest=LanguageLearningTest

# Run only enterprise form test
mvn test -Dtest=EnterpriseFormTest
```

### Option 3: Run the Original Project Class
```bash
mvn exec:java -Dexec.mainClass="com.coursera.automation.Project"
```

### Option 4: Run Multiple Tests
```bash
mvn test -Dtest=WebDevelopmentCoursesTest,EnterpriseFormTest
```

## Project Architecture

### Page Object Model (POM)

Each page in the application has a corresponding Page Object class:

**WebDevelopmentCoursesPage.java**
- Handles course search functionality
- Applies filters (difficulty, language)
- Extracts course information

**LanguageLearningPage.java**
- Searches for language courses
- Extracts available languages and levels
- Manages language and level filter dropdowns

**EnterpriseFormPage.java**
- Navigates to enterprise sections
- Fills and submits contact forms
- Validates form error messages

**BasePage.java**
- Common methods for all pages
- Wait strategies
- JavaScript execution utilities

### Test Classes

**WebDevelopmentCoursesTest.java**
- Tests web development course search
- Applies and validates filters
- Displays course information

**LanguageLearningTest.java**
- Tests language course search
- Extracts languages and proficiency levels
- Validates language filter functionality

**EnterpriseFormTest.java**
- Tests enterprise form submission
- Validates error messages
- Uses data-driven testing from properties

### Utility Classes

**ConfigReader.java**
- Reads properties from `config.properties`
- Provides type-safe property access (String, int, boolean)

**DriverManager.java**
- Initializes and manages WebDriver
- Handles browser options
- Manages driver lifecycle

## Configuration File

The `config.properties` file is the central place for test data and settings:

```properties
# Browser Settings
browser=chrome
baseURL=https://www.coursera.org
chrome.start.maximized=true
chrome.disable.notifications=true

# Wait Times (seconds)
explicit.wait.timeout=20
implicit.wait.timeout=10

# Test Data for Enterprise Form
firstName=TestUser
lastName=TestLast
invalidEmail=invalid-email-format

# Search Keywords
course.search.keyword=web development
language.search.keyword=Language Learning

# Filter Settings
course.difficulty=Beginner
course.language=English
course.limit=2
language.limit=5
```

## Data-Driven Testing Example

To change test data, simply edit `config.properties`:

```properties
firstName=John
lastName=Doe
invalidEmail=john@test.com
course.search.keyword=python programming
course.limit=5
```

No code changes needed! Tests will use the new data automatically.

## Test Output

When you run tests, you'll see output like:

```
--- 1. WEB DEVELOPMENT COURSES ---
Course Name: Course 1
Rating     : 4.8
Duration   : 4 weeks
--------------------------------------------------

--- 2. LANGUAGE & LEVEL EXTRACTION ---
[Processing Language: English]
  - Beginner
  - Intermediate
  - Advanced

--- 3. ENTERPRISE FORM VALIDATION ---
TERMINAL OUTPUT - ERROR MESSAGE:
******************************************
Please enter a valid email address
******************************************
```

## Maven Commands Reference

| Command | Description |
|---------|-------------|
| `mvn clean` | Clean build artifacts |
| `mvn compile` | Compile source code |
| `mvn test` | Run all tests |
| `mvn test -Dtest=ClassName` | Run specific test |
| `mvn clean install` | Clean, compile, and package |
| `mvn exec:java -Dexec.mainClass="com.coursera.automation.Project"` | Run main method |

## Project Files Overview

```
coursera-automation-project/
├── pom.xml                 # Maven dependencies and build config
├── testng.xml              # TestNG test suite configuration
├── README.md               # Detailed project documentation
├── USAGE.md                # This file
│
├── src/main/
│   ├── java/com/coursera/automation/
│   │   ├── Project.java            # Original unchanged code
│   │   ├── pages/                  # Page Object classes
│   │   └── utils/                  # Helper utilities
│   └── resources/
│       └── config.properties       # Test data and configuration
│
└── src/test/
    └── java/com/coursera/automation/tests/
        ├── BaseTest.java           # Base test setup/teardown
        └── *Test.java              # Individual test classes
```

## Troubleshooting

### Issue: "config.properties file not found"
**Solution:** Ensure file is in `src/main/resources/` and use `mvn clean install`

### Issue: "Chrome not found"
**Solution:** Install Google Chrome or update `browser=chrome` in config.properties

### Issue: "Tests timing out"
**Solution:** Increase timeout value in config.properties:
```properties
explicit.wait.timeout=30
```

### Issue: "WebDriver not found"
**Solution:** WebDriverManager will download automatically. Ensure internet connection is available.

## Original Project Code

The original `Project.java` file is fully preserved and can be run standalone:

```bash
mvn exec:java -Dexec.mainClass="com.coursera.automation.Project"
```

This maintains 100% backward compatibility with the original code.

## Next Steps

1. ✅ Verify project builds: `mvn clean install`
2. ✅ Run a test: `mvn test -Dtest=WebDevelopmentCoursesTest`
3. ✅ Update config.properties with your test data
4. ✅ Create additional tests following the same pattern
5. ✅ Check test reports in `target/surefire-reports/`

Happy Testing! 🚀
