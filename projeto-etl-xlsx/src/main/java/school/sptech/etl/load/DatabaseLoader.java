package school.sptech.etl.load;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class DatabaseLoader {

    public static void loadData(List<String> extractedData) {
        // Conectar ao banco de dados (exemplo com JDBC)
        String url = "jdbc:mysql://localhost:3306/flyon";
        String username = "root";
        String password = "Suave2004@";
        String query = "INSERT INTO historico_passagens (ano, mes, empresa_aerea, origem, destino, tarifa, assentos_comercializados) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Só alguns exemplos mockados aqui.
            int ano = 2023;
            int mes = 11;
            String empresa_aerea = "Exemplo Airlines";
            String origem = "GRU";
            String destino = "GIG";
            double tarifa = 500.00;


            int assentos_comercializados = Integer.parseInt(extractedData.get(4));

            stmt.setInt(1, ano);
            stmt.setInt(2, mes);
            stmt.setString(3, empresa_aerea);
            stmt.setString(4, origem);
            stmt.setString(5, destino);
            stmt.setDouble(6, tarifa);
            stmt.setInt(7, assentos_comercializados);

            stmt.executeUpdate();

            System.out.println("Dados carregados com sucesso!");

        } catch (Exception e) {
                System.err.println("Erro ao carregar dados: " + e.getMessage());
                e.printStackTrace();
        }
    }
}
