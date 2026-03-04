package org.botPromo;

import org.botPromo.bot.bd.Connections;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main() {
        try {
            Connection conn = Connections.getConexao();
            if(conn != null){
                System.out.println("Conectado com sucesso!");
            }
        }catch(Exception e){
            System.err.println("Erro ao conectar ao banco de dados " + e.getMessage());

        }


    }
}
