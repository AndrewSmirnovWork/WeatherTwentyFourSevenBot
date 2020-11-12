package api.bot;

import api.model.WeekWeatherModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class WeatherTwentyFourSevenBot extends TelegramLongPollingBot {

    private static final String TOKEN = "1114949041:AAHDS_SHmWHTLmBST6CCeV4soQ88OTMgR2Y";
    private static final String USERNAME = "WeatherTwentyFourSevenBot";

    private static final Logger LOG = LogManager.getLogger(WeatherTwentyFourSevenBot.class);


    public void onUpdateReceived(Update update) {

        if (update.getMessage() != null && update.getMessage().hasText()) {

            Message message = update.getMessage();
            // getting chat Id
            String chat_id = message.getChatId().toString();
            String text = message.getText();

            WeekWeatherModel weekWeatherModel = new WeekWeatherModel();

            try {
                switch (text) {
                    case ("Hello, what can u do"):
                        sendMsg(message, "Write name of the city to get forecast temperature");
                        break;
                    default:
                        //sendMsg(message, Weather.getCurrentWeather(text, weatherModel));
                        sendMsg(message, Weather.get5DaysWeather(message.getText(), weekWeatherModel));

                }
            } catch (Exception e) {
                LOG.error("Can't find the city!", e);
            }
        }
    }

    public synchronized void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        // разметка мешает парсить данные json?
        sendMessage.enableMarkdown(false);
        // setting chat id
        sendMessage.setChatId(message.getChatId());
        //message id
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        //set keyboards
        setButtons(sendMessage);
        setInline();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public synchronized void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        //выводить лквиатурур всем или только определенным пользователям
        replyKeyboardMarkup.setSelective(true);
        //"подгон" клавиатуры
        replyKeyboardMarkup.setResizeKeyboard(true);
        //скрывать клавиатуру после одного использования или нет
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        //create keyboard
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();

        //create first row and adding buttons
        KeyboardRow firstKeyboardRow = new KeyboardRow();
        firstKeyboardRow.add(new KeyboardButton("Hello, what can u do"));
/*
        //create first row and adding buttons
        KeyboardRow secondKeyboardRow = new KeyboardRow();
        secondKeyboardRow.add(new KeyboardButton("help"));*/


        // Добавляем все строчки клавиатуры в список
        keyboard.add(firstKeyboardRow);

        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);

    }

    private void setInline() {
        List<List<InlineKeyboardButton>> inlineButtons = new ArrayList<List<InlineKeyboardButton>>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<InlineKeyboardButton>();

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(inlineButtons);
    }

    public String getBotUsername() {
        return USERNAME;
    }

    public String getBotToken() {
        return TOKEN;
    }

}
