package org.botPromo.bot.services;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.botPromo.bot.configs.Configs;
import org.botPromo.bot.model.Produto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GeminiService {
    private static final String API_KEY = Configs.getApiKeyGemini();
    private static final Client client = Client.builder().apiKey(API_KEY).build();

    public static String pedirAnuncioParaIA(Produto produto) throws Exception{
            String prompt = "Aja como um especialista em ofertas. Crie um post para Telegram sobre o produto: "
                    + produto.getTitulo() + ", que custa R$ " + produto.getPreco() +
                    ". Use emojis chamativos, destaque o preço em negrito e termine com uma 'call to action' para o link: "
                    + produto.getLinkAfiliado() + ". Responda apenas com o texto final da oferta.";

            return chamarGemini(prompt);


    }
    public static boolean analisarOferta(Produto produto) {
        String prompt = "Aja como um especialista em compras e comparador de preços no Brasil (Março/2026). " +
                "Analise o seguinte produto: " + produto.getTitulo() + ". " +
                "Preço atual: R$ " + produto.getPreco() + ". " +
                "Sua tarefa: 1. Estime o preço médio de mercado para este produto NOVO em grandes varejistas (Amazon, ML, Magalu). " +
                "2. Verifique se R$ " + produto.getPreco() + " representa um desconto real de pelo menos 10% em relação à média. " +
                "3. Considere variações de modelo (ex: GB, polegadas, marca). " +
                "Responda APENAS 'true' se for uma oportunidade real de economia ou 'false' se o preço estiver na média ou caro. " +
                "Não explique, apenas uma palavra: true ou false.";


        String respostaIA = chamarGemini(prompt).trim().toLowerCase();

        System.out.println("🤖 Resposta da IA para " + produto.getTitulo() + ": " + respostaIA);

        return respostaIA.contains("true");
    }

    public static String chamarGemini(String prompt) {
       try {
           GenerateContentResponse response = client.models.generateContent(
                   "gemini-3-flash-preview",
                   prompt,
                   null
           );
           return response.text();
       }catch (Exception e){
           return "Erro: " + e.getMessage();
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
