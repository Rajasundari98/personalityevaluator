package com.example.personalityevaluator;

import java.util.HashMap;
import java.util.Map;

public class UserProfile {
    private String name;
    private Map<String, Integer> testResults;

    public UserProfile(String name) {
        this.name = name;
        this.testResults = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getTestResults() {
        return testResults;
    }

    public void addTestResult(String testName, int score) {
        testResults.put(testName, score);
    }

    public void setTestResults(Map<String, Integer> testResults) {
        this.testResults = testResults;
    }
}
