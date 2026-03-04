package org.botPromo.bot.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connections {
    private static final String URL = "jdbc:mysql://localhost:3306/bd_bot_promo";
    private static final String USER = "root";
    private static final String PASS = "";

    private static Connection conn;

    public static Connection getConexao() throws SQLException {
        if(conn == null ){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(URL, USER, PASS);
                System.out.println("A conexão foi feita corretamente");
            }catch(SQLException e){
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }
}
