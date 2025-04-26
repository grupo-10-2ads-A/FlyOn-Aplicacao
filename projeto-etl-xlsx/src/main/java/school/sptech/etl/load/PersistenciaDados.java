package school.sptech.etl.load;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.SQLException;

public class PersistenciaDados {

    private static final String URL = "jdbc:mysql://localhost:3306/flyon";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    public static void carregarTarifas(List<List<String>> dadosTarifas) {
        Connection conexao = null;
        PreparedStatement instrucao = null;

        try {
            // Conexão e desabilita autocommit
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            conexao.setAutoCommit(false);

            String query = "INSERT INTO voo_tarifa_historico (ano, mes, sigla_empresa_aerea, sigla_origem, sigla_destino, tarifa) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            instrucao = conexao.prepareStatement(query);

            for (List<String> cleanedDate : dadosTarifas) {
                instrucao.setString(1, cleanedDate.get(0));
                instrucao.setString(2, cleanedDate.get(1));
                instrucao.setString(3, cleanedDate.get(2));
                instrucao.setString(4, cleanedDate.get(3));
                instrucao.setString(5, cleanedDate.get(4));
                instrucao.setString(6, cleanedDate.get(5));

                instrucao.addBatch(); // Adiciona tudo de uma vez
            }

            instrucao.executeBatch(); // Executa todo o lote
            conexao.commit();       // Commit único
            System.out.println("Commit realizado - " + dadosTarifas.size() + " registros inseridos");

        } catch (SQLException e) {
            // Rollback em caso de erro
            if (conexao != null) {
                try {
                    conexao.rollback();
                    System.err.println("Transação revertida devido a erro");
                } catch (SQLException ex) {
                    System.err.println("Erro ao reverter transação: " + ex.getMessage());
                }
            }
            e.printStackTrace();
        } finally {
            // Fecha tudo
            try {
                if (instrucao != null) instrucao.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public static void carregarStatusVoos(List<List<String>> dadosHorarios,
                                          List<List<String>> dadosExtras,
                                          List<Integer> listaAssentos) {

        Connection conexao = null;
        PreparedStatement instrucao = null;

        try {
            // Conexão e desabilita autocommit
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            conexao.setAutoCommit(false);

            String query = "INSERT INTO voo_status_historico(data_hora_partida_prevista, data_hora_partida_real, " +
                    "data_hora_chegada_prevista, data_hora_chegada_real, sigla_empresa_aerea, " +
                    "empresa_aerea, origem, destino, situacao_voo, situacao_partida, " +
                    "situacao_chegada, assentos_comercializados) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            instrucao = conexao.prepareStatement(query);

            for (int i = 0; i < dadosHorarios.size(); i++) {
                List<String> cleanedDateTime = dadosHorarios.get(i);
                List<String> rawData = dadosExtras.get(i);
                Integer assentos = listaAssentos.get(i);

                instrucao.setString(1, cleanedDateTime.get(0));
                instrucao.setString(2, cleanedDateTime.get(1));
                instrucao.setString(3, cleanedDateTime.get(2));
                instrucao.setString(4, cleanedDateTime.get(3));
                instrucao.setString(5, rawData.get(0));
                instrucao.setString(6, rawData.get(1));
                instrucao.setString(7, rawData.get(2));
                instrucao.setString(8, rawData.get(3));
                instrucao.setString(9, rawData.get(4));
                instrucao.setString(10, rawData.get(5));
                instrucao.setString(11, rawData.get(6));
                instrucao.setInt(12, assentos);

                instrucao.addBatch(); // Adiciona tudo de uma vez
            }

            instrucao.executeBatch(); // Executa todo o lote
            conexao.commit();       // Commit único
            System.out.println("Commit realizado - " + dadosHorarios.size() + " registros inseridos");

        } catch (SQLException e) {
            // Rollback em caso de erro
            if (conexao != null) {
                try {
                    conexao.rollback();
                    System.err.println("Transação revertida devido a erro");
                } catch (SQLException ex) {
                    System.err.println("Erro ao reverter transação: " + ex.getMessage());
                }
            }
            e.printStackTrace();
        } finally {
            // Encerra transicao e conexao com o banco de dados
            try {
                if (instrucao != null) instrucao.close();
                if (conexao != null) conexao.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
