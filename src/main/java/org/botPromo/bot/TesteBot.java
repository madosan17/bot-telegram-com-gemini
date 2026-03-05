package org.botPromo.bot;

import org.botPromo.bot.model.Produto;
import org.botPromo.bot.services.GeminiService;
import org.botPromo.bot.telegram.TelegramService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TesteBot {
    public static void main(String[] args) throws Exception {
        List<Produto> produtos = new ArrayList<>();

        produtos.add(new Produto(
                "ML123",
                "iPhone 13 Apple (128GB) Meia-noite",
                "Tela Super Retina XDR de 6,1 polegadas",
                "https://http2.mlstatic.com/D_NQ_NP_634351-MLA47781591382_092021-O.webp",
                new BigDecimal("2200.00"),
                "https://www.mercadolivre.com.br/apple-iphone-13-128-gb-meia-noite/p/MLB18155138",
                "04/03/2026"
        ));

        produtos.add(new Produto(
                "ML456",
                "iPhone 13 Apple (128GB) Estelar",
                "Tela Super Retina XDR de 6,1 polegadas",
                "https://http2.mlstatic.com/D_NQ_NP_634351-MLA47781591382_092021-O.webp",
                new BigDecimal("5200.00"),
                "https://www.mercadolivre.com.br/apple-iphone-13-128-gb-estelar/p/MLB18155140",
                "04/03/2026"
        ));

        for(Produto p : produtos){
            System.out.println("Analisando o produto: " + p.getTitulo());
            System.out.println("Preco Atual: " + p.getPreco());

            try{
                boolean verificaIA = GeminiService.analisarOferta(p);

                if(verificaIA){
                    System.out.println("Produto aprovado com sucesso! preparando o envio de mensagem");

                    TelegramService.enviarMensagem(p);

                    System.out.println("Postado no telegram com sucesso!");
                }else{
                    System.out.println("IA rejeitou, o preço não é competitivo");
                }
            }catch(Exception e){
                System.err.println("Erro no processamento " + e.getMessage());
            }
        }

    }
}
