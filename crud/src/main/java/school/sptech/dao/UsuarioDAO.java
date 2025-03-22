package school.sptech.dao;

import school.sptech.model.Usuario;
import school.sptech.utils.ConexaoBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    //Método para adicionar um novo usuário ao banco de dados.
    public void adicionarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nome, email) VALUES (?, ?)"; // Query SQL para inserção.

        try (Connection conn = ConexaoBD.conectar(); // Abre a conexão com o banco.
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a query SQL.

            // Define os parâmetros da query com os valores do objeto Usuario.
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.executeUpdate(); // Executa a inserção no banco de dados.

            System.out.println("Usuário cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage()); // Captura e exibe erros de SQL.
        }
    }

    //Método para listar todos os usuários cadastrados no banco de dados.
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>(); // Lista para armazenar os usuários.
        String sql = "SELECT * FROM usuarios"; // Query SQL para buscar todos os usuários.

        try (Connection conn = ConexaoBD.conectar(); // Abre a conexão com o banco.
             Statement stmt = conn.createStatement(); // Cria um statement para executar a query.
             ResultSet rs = stmt.executeQuery(sql)) { // Executa a consulta SQL e armazena os resultados.

            // Itera sobre os resultados retornados pelo banco de dados.
            while (rs.next()) {
                Usuario usuario = new Usuario(); // Cria um novo objeto Usuario.
                usuario.setId(rs.getInt("id")); // Define o ID do usuário.
                usuario.setNome(rs.getString("nome")); // Define o nome do usuário.
                usuario.setEmail(rs.getString("email")); // Define o email do usuário.
                usuarios.add(usuario); // Adiciona o usuário à lista.
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage()); // Captura e exibe erros de SQL.
        }

        return usuarios; // Retorna a lista de usuários encontrados.
    }


     // Método para atualizar os dados de um usuário existente no banco de dados.
    public void atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, email = ? WHERE id = ?"; // Query SQL para atualização.

        try (Connection conn = ConexaoBD.conectar(); // Abre a conexão com o banco.
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a query SQL.

            // Define os parâmetros da query com os valores do objeto Usuario.
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setInt(3, usuario.getId());
            stmt.executeUpdate(); // Executa a atualização no banco de dados.

            System.out.println("Usuário atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar usuário: " + e.getMessage()); // Captura e exibe erros de SQL.
        }
    }


     // Método para excluir um usuário do banco de dados pelo ID.
    public void excluirUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?"; // Query SQL para exclusão.

        try (Connection conn = ConexaoBD.conectar(); // Abre a conexão com o banco.
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepara a query SQL.

            stmt.setInt(1, id); // Define o ID do usuário a ser excluído.
            stmt.executeUpdate(); // Executa a exclusão no banco de dados.

            System.out.println("Usuário removido com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao excluir usuário: " + e.getMessage()); // Captura e exibe erros de SQL.
        }
    }
}
