package org.botPromo.bot.services;

import org.botPromo.bot.dao.ProdutoDAO;
import org.botPromo.bot.model.Produto;

import java.io.IOException;

public class ProdutoService {
    private ProdutoDAO dao = new ProdutoDAO();

    public void processarProduto(Produto produto) throws Exception {
        System.out.println("analisando o produto: " + produto.getTitulo());

        boolean verificadoIA = GeminiService.analisarOferta(produto);

        if(verificadoIA){
            System.out.println("Oferta aprovada com sucesso pela IA, Salvando arquivo");

            dao.adicionarProduto(produto);


        }else{
            System.out.println("Oferta recusada pela IA. Preço acima da média");
        }


    }
}
