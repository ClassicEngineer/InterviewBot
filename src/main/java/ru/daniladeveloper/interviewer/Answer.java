package ru.daniladeveloper.interviewer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class Answer {

    private static final String BASE_URL = "https://github.com/enhorse/java-interview/blob/master/";

    public String value;
    public String href;

    public Answer(String value, String line) {
        this.value = value;
        this.href = BASE_URL + line;
    }

    public Answer() {
        this.value ="bruh";
        this.href = "https://google.com";
    }
}
