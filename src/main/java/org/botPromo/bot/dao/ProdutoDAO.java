package org.botPromo.bot.dao;

import org.botPromo.bot.bd.Connections;
import org.botPromo.bot.model.Produto;

import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProdutoDAO {

    public void adicionarProduto(Produto produto){
        String sql = "INSERT INTO produtos (id,titulo,descricao,imagem_url,preco,link_afiliado,data_postagem) VALUES (?,?,?,?,?,?,?)";
        try(Connection conn = Connections.getConexao();
            PreparedStatement stm = conn.prepareStatement(sql);){

            stm.setString(1, produto.getId());
            stm.setString(2,produto.getTitulo());
            stm.setString(3, produto.getDescricao());
            stm.setString(4,produto.getImagemUrl());
            stm.setBigDecimal(5,produto.getPreco());
            stm.setString(6, produto.getLinkAfiliado());
            stm.setString(7,produto.getDataPostagem());
            stm.executeUpdate();
            System.out.println("Produto salvo no banco de dados " + produto.getTitulo());

        }catch(SQLException e){
            System.out.println("Erro ao inserir o produto no banco de dados " + e.getMessage());
        }
    }

    public boolean existeProduto(Produto produto){
        String sql = "SELECT id FROM produtos WHERE id = ?";
        try(Connection conn = Connections.getConexao();
            PreparedStatement stm = conn.prepareStatement(sql);){

            stm.setString(1, produto.getId());

            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                return true;
            }
        }catch(SQLException e){
            System.out.println("Erro ao buscar o produto no banco de dados " + e.getMessage());
        }
        return false;
    }

}
