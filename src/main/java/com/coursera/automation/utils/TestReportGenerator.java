import java.io.*;
import java.util.*;

public class TestReportGenerator {

    private List<TestResult> testResults;

    public TestReportGenerator() {
        testResults = new ArrayList<>();
    }

    public void addTestResult(TestResult result) {
        testResults.add(result);
    }

    public void generateReport(String format) {
        // code to generate report in specified format (HTML, JSON, CSV, TXT)
    }

    public static class TestResult {
        private String testName;
        private boolean passed;
        private long executionTime;

        // Constructors, getters, and setters
    }
}