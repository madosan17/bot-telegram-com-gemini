package org.botPromo.bot.services;

import org.botPromo.bot.configs.Configs;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.botPromo.bot.model.Produto;
import org.json.JSONArray;
import org.json.JSONObject;

public class MercadoLivreService {

    private static final String ACCESS_TOKEN = Configs.getAcess_Token();

    public static String gerarLinkAfiliado(String urlOriginal) {
        try {

            String urlApi = "https://api.mercadolibre.com/affiliates/MLB/short_urls?access_token=" + ACCESS_TOKEN;

            JSONObject item = new JSONObject();
            item.put("url", urlOriginal);

            JSONArray bodyArray = new JSONArray();
            bodyArray.put(item);

            String jsonBody = bodyArray.toString();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlApi))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201 || response.statusCode() == 200) {
                JSONArray resArray = new JSONArray(response.body());
                return resArray.getJSONObject(0).getString("short_url");
            } else {
                System.out.println("❌ Erro na API de Afiliados: " + response.body());
                return urlOriginal;
            }
        } catch (Exception e) {
            System.out.println("❌ Erro no processamento: " + e.getMessage());
            return urlOriginal;
        }
    }

    public static Produto processarProduto(String itemId) {
        Produto produto = new Produto();
        try {

            String url = "https://api.mercadolibre.com/items?ids=" + itemId +
                    "&attributes=id,price,title,pictures,permalink&access_token=" + ACCESS_TOKEN;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().
                    uri(URI.create(url))
                    .header("Authorization", "Bearer " + ACCESS_TOKEN)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            JSONArray jsonArray = new JSONArray(response.body());
            JSONObject primeiraResposta = jsonArray.getJSONObject(0);


            if (primeiraResposta.getInt("code") == 200) {

                JSONObject itemData = primeiraResposta.getJSONObject("body");

                String titulo = itemData.getString("title");
                double preco = itemData.getDouble("price");
                String linkOriginal = itemData.getString("permalink");


                String linkAfiliado = gerarLinkAfiliado(linkOriginal);

                String urlFoto = itemData.getJSONArray("pictures").getJSONObject(0).getString("secure_url");

                produto = new Produto(
                        itemId,
                        titulo,
                        "",
                        urlFoto,
                        new BigDecimal(preco),
                        linkAfiliado,
                        ""
                );

                System.out.println("✅ Produto: " + titulo + " | R$ " + preco);
            } else {
                System.out.println("❌ Produto não encontrado ou erro: " + primeiraResposta.getInt("code"));
            }
        } catch (Exception e) {
            System.out.println("❌ Erro ao processar: " + e.getMessage());
        }
        return produto;
    }

    public static void processarComCerteza(String itemId) {
        try {
            String url = "https://api.mercadolibre.com//items?ids=notebook";
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Authorization", "Bearer " + ACCESS_TOKEN.trim())
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .header("Accept", "application/json")
                    // REMOVIDO: .header("Connection", "keep-alive") -> O Java não deixa mexer aqui
                    .GET()
                    .build();

            System.out.println("🔍 Testando conexão com: " + url);

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("📊 STATUS: " + response.statusCode());

            if (response.statusCode() == 200) {
                System.out.println("✅ SUCESSO!");
                System.out.println("Conteúdo: " + response.body().substring(0, 100));
            } else {
                System.out.println("❌ Erro 403 ou similar. Resposta do ML:");
                System.out.println(response.body());
            }

        } catch (Exception e) {
            System.out.println("❌ Erro no código: " + e.getMessage());
        }
    }
}



