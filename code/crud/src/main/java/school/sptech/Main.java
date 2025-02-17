package school.sptech;

import school.sptech.dao.UsuarioDAO;
import school.sptech.model.Usuario;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Instancia um Scanner para capturar entrada do usuário.
        UsuarioDAO usuarioDAO = new UsuarioDAO(); // Instancia a classe DAO para manipulação de usuários no banco de dados.
        int opcao; // Variável para armazenar a opção do usuário no menu.

        // Loop do menu principal
        do {
            // Exibe as opções do menu
            System.out.println("\n### Gerenciamento de Usuários ###");
            System.out.println("1. Adicionar Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Atualizar Usuário");
            System.out.println("4. Excluir Usuário");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt(); // Lê a opção escolhida pelo usuário.
            scanner.nextLine(); // Consome a quebra de linha pendente.

            switch (opcao) {
                case 1: // Opção para adicionar um novo usuário.
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine(); // Captura o nome do usuário.
                    System.out.print("Email: ");
                    String email = scanner.nextLine(); // Captura o email do usuário.

                    // Chama o método para adicionar um novo usuário ao banco de dados.
                    usuarioDAO.adicionarUsuario(new Usuario(0, nome, email));
                    break;

                case 2: // Opção para listar todos os usuários cadastrados.
                    List<Usuario> usuarios = usuarioDAO.listarUsuarios(); // Obtém a lista de usuários.

                    // Exibe cada usuário da lista no console.
                    usuarios.forEach(System.out::println);
                    break;

                case 3: // Opção para atualizar os dados de um usuário existente.
                    System.out.print("ID do usuário a atualizar: ");
                    int idAtualizar = scanner.nextInt(); // Captura o ID do usuário a ser atualizado.
                    scanner.nextLine(); // Consome a quebra de linha pendente.

                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine(); // Captura o novo nome do usuário.
                    System.out.print("Novo email: ");
                    String novoEmail = scanner.nextLine(); // Captura o novo email do usuário.

                    // Atualiza os dados do usuário no banco de dados.
                    usuarioDAO.atualizarUsuario(new Usuario(idAtualizar, novoNome, novoEmail));
                    break;

                case 4: // Opção para excluir um usuário pelo ID.
                    System.out.print("ID do usuário a excluir: ");
                    int idExcluir = scanner.nextInt(); // Captura o ID do usuário a ser excluído.

                    // Chama o método para excluir o usuário do banco de dados.
                    usuarioDAO.excluirUsuario(idExcluir);
                    break;
            }
        } while (opcao != 0); // O loop continua até que o usuário escolha a opção de saída (0).

        scanner.close(); // Fecha o Scanner para evitar vazamento de recursos.
    }
}
