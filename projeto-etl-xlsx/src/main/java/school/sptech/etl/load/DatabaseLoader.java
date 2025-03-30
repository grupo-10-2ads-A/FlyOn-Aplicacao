package school.sptech.etl.load;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class DatabaseLoader {

    public static void loadData(List<String> cleanedDateTime, List<String> rawData, int assentos_comercializados) {
        // Conectar ao banco de dados (exemplo com JDBC)
        String url = "jdbc:mysql://localhost:3306/flyon";
        String username = "root";
        String password = "MAXSTEEl08#";
        String query = "INSERT INTO historico_passagens(data_hora_partida_prevista, data_hora_partida_real, data_hora_chegada_prevista, data_hora_chegada_real, sigla_empresa_aerea, empresa_aerea, origem, destino, situacao_voo, situacao_partida, situacao_chegada, assentos_comercializados) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String data_hora_partida_prevista = cleanedDateTime.get(0);
            String data_hora_partida_real = cleanedDateTime.get(1);
            String data_hora_chegada_prevista = cleanedDateTime.get(2);
            String data_hora_chegada_real = cleanedDateTime.get(3);
            String sigla_empresa_aerea = rawData.get(0);
            String empresa_aerea = rawData.get(1);
            String origem = rawData.get(2);
            String destino = rawData.get(3);
            String situacao_voo = rawData.get(4);
            String situacao_partida = rawData.get(5);
            String situacao_chegada = rawData.get(6);

            stmt.setString(1, data_hora_partida_prevista);
            stmt.setString(2, data_hora_partida_real);
            stmt.setString(3, data_hora_chegada_prevista);
            stmt.setString(4, data_hora_chegada_real);
            stmt.setString(5, sigla_empresa_aerea);
            stmt.setString(6, empresa_aerea);
            stmt.setString(7, origem);
            stmt.setString(8, destino);
            stmt.setString(9, situacao_voo);
            stmt.setString(10, situacao_partida);
            stmt.setString(11, situacao_chegada);
            stmt.setInt(12, assentos_comercializados);

            stmt.executeUpdate();

            System.out.println("Dados carregados com sucesso!");

        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
