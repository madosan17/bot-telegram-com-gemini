package org.botPromo.bot.configs;

import io.github.cdimascio.dotenv.Dotenv;

public class Configs {
    private static final Dotenv dotenv = Dotenv.load();

    public static String getClienteId(){
        return dotenv.get("ML_CLIENTE_ID");
    }
    public static String getCLienteSecret(){
        return dotenv.get("ML_CLIENTE_SECRET");
    }
    public static String getApiKeyGemini(){
        return dotenv.get("GEMINI_API_KEY");
    }
    public static String getBotToken(){
        return dotenv.get("TOKEN_TELEGRAM");
    }
}
