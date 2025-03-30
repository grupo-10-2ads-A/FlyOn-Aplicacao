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
        String query = "INSERT INTO historico_passagens (ano, empresa_aerea, origem, destino, assentos_comercializados) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String dataPartida = extractedData.get(0); // "01/01/2022 00:05"
            int ano = Integer.parseInt(dataPartida.split("/")[2].split(" ")[0]);

            // Só alguns exemplos mockados aqui.
//                int ano = 2023;
//            int mes = 11;
//            String empresa_aerea = "Exemplo Airlines";
//            String origem = "GRU";
//            String destino = "GIG";
//            double tarifa = 500.00;


            String empresa_aerea = extractedData.get(4);
            String origem = extractedData.get(5);
            String destino = extractedData.get(6);
            int assentos_comercializados = Integer.parseInt(extractedData.get(7));

            stmt.setInt(1, ano);
            stmt.setString(2, empresa_aerea);
            stmt.setString(3, origem);
            stmt.setString(4, destino);
            stmt.setInt(5, assentos_comercializados);

            System.out.println("Dados a serem inseridos:");
            System.out.println("Ano: " + ano);
            System.out.println("Empresa: " + empresa_aerea);
            System.out.println("Origem: " + origem);
            System.out.println("Destino: " + destino);
            System.out.println("Assentos: " + assentos_comercializados);

            stmt.executeUpdate();

            System.out.println("Dados carregados com sucesso!");

        } catch (Exception e) {
                System.err.println("Erro ao carregar dados: " + e.getMessage());
                e.printStackTrace();
        }
    }
}
