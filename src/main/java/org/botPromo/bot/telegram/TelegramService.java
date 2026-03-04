package org.botPromo.bot.telegram;

import org.botPromo.bot.configs.Configs;
import org.botPromo.bot.model.Produto;
import org.botPromo.bot.services.GeminiService;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class TelegramService {


    public static String enviarMensagem(Produto produto){
        try{
            System.out.println(" IA gerando legenda para " + produto.getTitulo());
            String legendaIA = GeminiService.pedirAnuncioParaIA(produto);

            String textoFormatado = URLEncoder.encode(legendaIA, StandardCharsets.UTF_8);

            String botToken = Configs.getBotToken();
            String chatId = "7877909169";

            String urlTelegram = "https://api.telegram.org/bot" + botToken + "/sendPhoto" +
                    "?chat_id=" + chatId +
                    "&photo=" + produto.getImagemUrl() + // Link da foto que veio do ML
                    "&caption=" + textoFormatado +
                    "&parse_mode=HTML";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlTelegram)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200){
                return "Sucesso mensagem enviada para o telegram" + "Detalhes: " + response.body();
            }else{
                return "Erro : " + response.statusCode();
            }
        }catch (Exception e){
            return ("Ocorreu um erro com o java " + e.getMessage());
        }
    }

}
