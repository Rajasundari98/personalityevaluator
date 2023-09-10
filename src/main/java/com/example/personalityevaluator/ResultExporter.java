
package com.example.personalityevaluator;

        import java.io.FileWriter;
        import java.io.IOException;
        import java.util.List;
        import java.util.Map;

public class ResultExporter {
    public static void saveProfilesToFile(String fileName, List<UserProfile> profiles) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (UserProfile userProfile : profiles) {
                writer.write(userProfile.getName() + ",");
                Map<String, Integer> testResults = userProfile.getTestResults();
                for (Map.Entry<String, Integer> entry : testResults.entrySet()) {
                    writer.write(entry.getKey() + ":" + entry.getValue() + ",");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
