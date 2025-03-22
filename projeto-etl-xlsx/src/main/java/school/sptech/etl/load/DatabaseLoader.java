package school.sptech.etl.load;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class DatabaseLoader {

    public static void loadData(String natureza, List<Integer> numeros) {
        // Conectar ao banco de dados (exemplo com JDBC)
        String url = "jdbc:mysql://localhost:3306/teste";
        String username = "seuusuario";
        String password = "suasenha";
        String query = "INSERT INTO tabela_dados (natureza, janeiro, fevereiro, marco, abril, maio, junho, julho, agosto, setembro, outubro, novembro, dezembro, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, natureza);
            stmt.setInt(2, numeros.get(0));
            stmt.setInt(3, numeros.get(1));
            stmt.setInt(4, numeros.get(2));
            stmt.setInt(5, numeros.get(3));
            stmt.setInt(6, numeros.get(4));
            stmt.setInt(7, numeros.get(5));
            stmt.setInt(8, numeros.get(6));
            stmt.setInt(9, numeros.get(7));
            stmt.setInt(10, numeros.get(8));
            stmt.setInt(11, numeros.get(9));
            stmt.setInt(12, numeros.get(10));
            stmt.setInt(13, numeros.get(11));
            stmt.setInt(14, numeros.get(12));

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
