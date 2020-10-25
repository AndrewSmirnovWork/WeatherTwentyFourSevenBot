package api.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class BotInitializer {

    private static final Logger LOG = LogManager.getLogger(BotInitializer.class);

    public static void main(String[] args) {

        try {

            // Авторизация бота в прокси, после создания будет использоваться автоматически
            LOG.info("Initializing API context...");
            ApiContextInitializer.init();

            //Create the TelegramBotsApi object to register your bots
            TelegramBotsApi botsApi = new TelegramBotsApi();

            // Set up Http proxy
            LOG.info("Configuring bot options...");
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
            botOptions.setProxyHost("localhost");
            botOptions.setProxyPort(3128);
            botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

            WeatherTwentyFourSevenBot bot = new WeatherTwentyFourSevenBot();

            botsApi.registerBot(bot);
            LOG.info("WeatherTwentyFourSevenBot bot is ready for work!");

        } catch (TelegramApiRequestException e) {
            LOG.error("Error while initializing bot!", e);
        }
    }
}
