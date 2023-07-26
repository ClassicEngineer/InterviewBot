package ru.daniladeveloper.interviewer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class InterviewStorage {

    private final Random random = new Random();

    private HashMap<Question, Answer> storage;



    public InterviewStorage() {
        this.storage = new HashMap<>();
        load();
    }

    public Map.Entry<Question, Answer> getRandomTask() {
        int index = random.nextInt(storage.size());
        var list = storage.entrySet().stream().toList();
        return list.get(index - 1);
    }

    public  void load() {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            URL resource = classloader.getResource("static/interview.md");
            List<String> lines = Files.readAllLines(Path.of(resource.toURI()));
            for (String line : lines) {
                String[] qa = line.split("]\\(");
                Question question = new Question(qa[0]);
                Answer answer = new Answer("bruh", qa[1]);
                storage.put(question, answer);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
