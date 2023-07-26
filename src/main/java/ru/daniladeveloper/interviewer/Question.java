package ru.daniladeveloper.interviewer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class Question {

    private String value;

    public Question(String line) {
        this.value = line.trim();
    }
}
