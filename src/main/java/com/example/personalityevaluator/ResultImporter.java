
package com.example.personalityevaluator;

        import java.io.BufferedReader;
        import java.io.FileReader;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class ResultImporter {
    public static List<UserProfile> loadProfilesFromFile(String fileName) {
        List<UserProfile> profiles = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                Map<String, Integer> testResults = new HashMap<>();
                for (int i = 1; i < parts.length; i++) {
                    String[] result = parts[i].split(":");
                    if (result.length == 2) {
                        String testName = result[0];
                        int score = Integer.parseInt(result[1]);
                        testResults.put(testName, score);
                    }
                }
                UserProfile userProfile = new UserProfile(name);
                userProfile.setTestResults(testResults);
                profiles.add(userProfile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return profiles;
    }
}
