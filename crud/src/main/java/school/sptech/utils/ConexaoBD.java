package school.sptech.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
     private static final String URL = "jdbc:mysql://localhost:3306/crudTeste";
    private static final String USER = "root";
    private static final String PASSWORD = "Dudas@1608";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Erro na conexão com o banco de dados!", e);
        }
    }
}
