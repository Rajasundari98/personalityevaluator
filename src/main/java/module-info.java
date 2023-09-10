module com.example.personalityevaluator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.personalityevaluator to javafx.fxml;
    exports com.example.personalityevaluator;
}