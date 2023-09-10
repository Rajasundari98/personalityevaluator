package com.example.personalityevaluator;

import javafx.application.Application;
        import javafx.geometry.Insets;
        import javafx.geometry.Pos;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.layout.*;
        import javafx.scene.paint.Color;
        import javafx.stage.Stage;

        import java.io.*;
        import java.util.*;

public class PersonalityEvaluatorApp extends Application {
    private final List<String> extrovertQuestions = new ArrayList<>();
    private final List<String> introvertQuestions = new ArrayList<>();
    private int extrovertScore = 0;
    private int introvertScore = 0;
    private int questionIndex = 0;
    private List<UserProfile> userProfiles = new ArrayList<>();

    private Label questionLabel;
    private ToggleGroup answerGroup;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Personality Evaluator");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Load questions from external files
        extrovertQuestions.addAll(loadQuestionsFromFile("extrovert_questions.txt"));
        introvertQuestions.addAll(loadQuestionsFromFile("introvert_questions.txt"));

        // GUI components
        Label titleLabel = new Label("Personality Type Evaluator");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        questionLabel = new Label();
        questionLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox questionBox = new VBox(10);
        questionBox.setAlignment(Pos.CENTER_LEFT);

        answerGroup = new ToggleGroup();

        VBox answerBox = new VBox(10);
        answerBox.setAlignment(Pos.CENTER_LEFT);

        RadioButton rb1 = new RadioButton("1 - Strongly Disagree");
        RadioButton rb2 = new RadioButton("2 - Disagree");
        RadioButton rb3 = new RadioButton("3 - Neutral");
        RadioButton rb4 = new RadioButton("4 - Agree");
        RadioButton rb5 = new RadioButton("5 - Strongly Agree");

        rb1.setToggleGroup(answerGroup);
        rb2.setToggleGroup(answerGroup);
        rb3.setToggleGroup(answerGroup);
        rb4.setToggleGroup(answerGroup);
        rb5.setToggleGroup(answerGroup);

        answerBox.getChildren().addAll(rb1, rb2, rb3, rb4, rb5);

        Button nextButton = new Button("Next");
        nextButton.setDefaultButton(true);
        nextButton.setOnAction(event -> handleNextButton());

        root.setTop(titleLabel);
        root.setCenter(questionLabel);
        root.setLeft(answerBox);
        root.setBottom(nextButton);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        displayNextQuestion();
    }

    private void handleNextButton() {
        RadioButton selectedRadioButton = (RadioButton) answerGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            int answer = Integer.parseInt(selectedRadioButton.getText().split(" - ")[0]);
            extrovertScore += getAnswerScore(answer);
            introvertScore += getAnswerScore(6 - answer);

            questionIndex++;
            if (questionIndex < extrovertQuestions.size() + introvertQuestions.size()) {
                displayNextQuestion();
                answerGroup.getSelectedToggle().setSelected(false);
            } else {
                displayResult();
            }
        }
    }

    private void displayNextQuestion() {
        if (questionIndex < extrovertQuestions.size()) {
            questionLabel.setText(extrovertQuestions.get(questionIndex));
        } else if (questionIndex - extrovertQuestions.size() < introvertQuestions.size()) {
            questionLabel.setText(introvertQuestions.get(questionIndex - extrovertQuestions.size()));
        }
    }

    private void displayResult() {
        String result;
        if (extrovertScore > introvertScore) {
            result = "You are an extrovert.";
        } else if (extrovertScore < introvertScore) {
            result = "You are an introvert.";
        } else {
            result = "You are an ambivert.";
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Result");
        alert.setHeaderText(null);
        alert.setContentText(result);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                restartEvaluation();
            } else {
                System.exit(0);
            }
        });
    }

    private void restartEvaluation() {
        extrovertScore = 0;
        introvertScore = 0;
        questionIndex = 0;
        displayNextQuestion();
        answerGroup.getSelectedToggle().setSelected(false);
    }

    private int getAnswerScore(int answer) {
        // Ensure the answer is within the valid range (1-5)
        return Math.max(1, Math.min(5, answer));
    }

    private List<String> loadQuestionsFromFile(String fileName) {
        List<String> questions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                questions.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

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

