package org.botPromo.bot.telegram;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EnvioTelegram {

    public static void main(String[] args){
        String token = "8607877528:AAFWEC-IVd-Kzir6cOsCTmw3xmpgfxZukvI";
        String chatId = "7877909169";
        String mensagem = "Matheus, vimos as melhores ofertas para você";

        String mensagemFormatada = mensagem.replace(" ", "%20");

        String urlFinal = "https://api.telegram.org/bot" + token +
                            "/sendMessage?chat_id=" + chatId +
                            "&text=" + mensagemFormatada;

        try{
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlFinal)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.statusCode() == 200){
                System.out.println("Sucesso mensagem enviada para o telegram");
                System.out.println("Detalhes: " + response.body());
            }else{
                System.out.println("Erro : " + response.statusCode());
            }
        }catch (Exception e){
            System.out.println("Ocorreu um erro com o java " + e.getMessage());
            e.printStackTrace();
        }
    }
}
