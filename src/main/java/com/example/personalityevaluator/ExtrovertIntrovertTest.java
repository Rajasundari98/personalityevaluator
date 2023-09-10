
package com.example.personalityevaluator;

        import java.util.List;
        import java.util.ArrayList;

public class ExtrovertIntrovertTest implements PersonalityTest {
    private List<String> questions;
    private int[] answers;

    public ExtrovertIntrovertTest() {
        questions = new ArrayList<>();
        // Load extrovert questions from external file or define them here
        // Example:
        questions.add("I enjoy socializing and being around people.");
        questions.add("I am energized by social interactions.");
        questions.add("I find it easy to start conversations with strangers.");
        // Add more questions...

        answers = new int[questions.size()];
    }

    @Override
    public List<String> getQuestions() {
        return questions;
    }

    @Override
    public void recordAnswer(int questionIndex, int answer) {
        // Ensure the answer is within the valid range (1-5)
        answer = Math.max(1, Math.min(5, answer));
        answers[questionIndex] = answer;
    }

    @Override
    public String getResult() {
        int extrovertScore = 0;
        int introvertScore = 0;

        for (int i = 0; i < questions.size(); i++) {
            int answer = answers[i];
            extrovertScore += answer;
            introvertScore += 6 - answer;
        }

        if (extrovertScore > introvertScore) {
            return "You are an extrovert.";
        } else if (extrovertScore < introvertScore) {
            return "You are an introvert.";
        } else {
            return "You are an ambivert.";
        }
    }
}
