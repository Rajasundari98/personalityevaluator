

package com.example.personalityevaluator;

import java.util.List;

public interface PersonalityTest {
    List<String> getQuestions();
    void recordAnswer(int questionIndex, int answer);
    String getResult();
}
