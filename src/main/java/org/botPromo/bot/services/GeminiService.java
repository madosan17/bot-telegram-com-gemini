package org.botPromo.bot.services;

import org.botPromo.bot.configs.Configs;
import org.botPromo.bot.model.Produto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeminiService {
    private static final String API_KEY = Configs.getApiKeyGemini();
    private static final String URL_API = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=" + API_KEY;

    public static String pedirAnuncioParaIA(Produto produto) throws Exception{
            String prompt = "Aja como um especialista em ofertas. Crie um post para Telegram sobre o produto: "
                    + produto.getTitulo() + ", que custa R$ " + produto.getPreco() +
                    ". Use emojis chamativos, destaque o preço em negrito e termine com uma 'call to action' para o link: "
                    + produto.getLinkAfiliado() + ". Responda apenas com o texto final da oferta.";

            return chamarGemini(prompt);


    }
    public static boolean analisarOferta(Produto produto) throws Exception{
            String prompt = "Analise o preço deste produto: " + produto.getTitulo() + " por R$ " + produto.getPreco() + ". " +
                    "Baseado no mercado brasileiro de 2024/2025, o preço está excelente? " +
                    "Responda APENAS com a palavra 'true' ou 'false'. Não use pontos ou explicações.";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_API))
                    .header("content_type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(prompt))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String respostaIA = chamarGemini(prompt).trim().toLowerCase();

            return respostaIA.contains("true");
    }

    public static String chamarGemini(String prompt){
        try{
            String jsonBody = "{ \"contents\": [{ \"parts\":[{ \"text\": \"" + prompt + "\" }] }] }";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL_API))
                    .header("content_type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return extrairTextoDoJSON(response.body());
            } else {
                return "Erro na IA: " + response.statusCode();
            }
        }catch(Exception e){
            return "Falha ao se conectar ao Gemini " + e.getMessage();
        }
    }



    private static String extrairTextoDoJSON(String json) {
        try {
            int inicio = json.indexOf("\"text\": \"") + 9;
            int fim = json.indexOf("\"", inicio);
            return json.substring(inicio, fim).replace("\\n", "\n").replace("\\\"", "\"");
        } catch (Exception e) {
            return "Erro ao processar resposta da IA.";
        }
    }
}
