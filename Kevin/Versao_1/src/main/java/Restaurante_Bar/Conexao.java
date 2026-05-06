package Restaurante_Bar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String HOST = "localhost";
    private static final String PORTA = "3306";
    private static final String BANCO = "restaurante";
    private static final String USER = "root";
    private static final String PASSWORD = "guiomar";

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORTA + "/" + BANCO +
            "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";

    public static Connection conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC do MySQL não encontrado.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com MySQL: " + e.getMessage(), e);
        }
    }
}