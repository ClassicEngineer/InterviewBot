package ru.daniladeveloper.interviewer;

import lombok.extern.java.Log;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Log
public class InterviewBot extends TelegramLongPollingBot {

    private final InterviewStorage interviewStorage;
    private final Properties properties;
    private final Map<Long, Answer> chatOnLastAnswer;

    public  InterviewBot() {
        interviewStorage = new InterviewStorage();
        chatOnLastAnswer = new ConcurrentHashMap<>();
        properties = new Properties();
        try {
            properties.load(InterviewBot.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
    }

    @Override
    public String getBotToken() {
        return properties.getProperty("botToken");
    }

    @Override
    public String getBotUsername() {
        return properties.getProperty("botUserName");
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String incomeText = update.getMessage().getText();
            SendMessage message = new SendMessage();
            Long chatId = update.getMessage().getChatId();
            message.setChatId(chatId.toString());
            String messageText = "";
            switch (incomeText) {
                case "/start" -> {
                    messageText = "You wanna play? Let's play!";
                }
                case "/ask" -> {
                    var task = interviewStorage.getRandomTask();
                    messageText = task.getKey().getValue();
                    chatOnLastAnswer.put(chatId, task.getValue());
                }
                case "/ans" -> {
                    messageText = "See here: " + chatOnLastAnswer.get(chatId).href;
                }
                default -> {
                    messageText = "We are so sorry!";
                }
            }


            message.setText(messageText);

            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}
