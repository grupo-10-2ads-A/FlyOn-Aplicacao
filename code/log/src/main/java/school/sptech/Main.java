package school.sptech;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        // Inicia o sistema e executa todas as etapas do processamento de voos
        log("Iniciando o sistema de análise de voos...");

        receberDados();
        analisarDados();
        limparDados();
        enviarParaDashboard();

        log("Processamento concluído com sucesso!");
    }

    /**
     * Simula o recebimento de dados de voos, indicando a quantidade de registros processados.
     */
    private static void receberDados() {
        log("Recebendo dados de voos domésticos...");
        simularProcessamento(2000); // Simula um tempo de processamento de 2 segundos
        log("Dados recebidos: 50.000 registros carregados.");
    }

    /**
     * Simula a análise dos dados, identificando possíveis erros nos registros.
     */
    private static void analisarDados() {
        log("Analisando os dados recebidos...");
        simularProcessamento(3000); // Simula um tempo de processamento de 3 segundos
        int errosDetectados = new Random().nextInt(500); // Gera um número aleatório de registros inconsistentes
        log("Análise concluída: " + errosDetectados + " registros inconsistentes encontrados.");
    }

    /**
     * Simula a limpeza dos dados inconsistentes detectados na etapa anterior.
     */
    private static void limparDados() {
        log("Limpando os dados inconsistentes...");
        simularProcessamento(2500); // Simula um tempo de processamento de 2,5 segundos
        log("Processo de limpeza concluído. Dados prontos para envio.");
    }

    /**
     * Simula o envio dos dados limpos para um dashboard para visualização.
     */
    private static void enviarParaDashboard() {
        log("Enviando dados limpos para o dashboard...");
        simularProcessamento(1500); // Simula um tempo de processamento de 1,5 segundos
        log("Envio finalizado: 49.500 registros disponíveis para visualização.");
    }

    /**
     * Simula um tempo de processamento pausando a execução da thread por um determinado tempo.
     * @param tempo Tempo em milissegundos que a simulação deve durar.
     */
    private static void simularProcessamento(int tempo) {
        try {
            Thread.sleep(tempo); // Pausa a execução por 'tempo' milissegundos
        } catch (InterruptedException e) {
            log("Erro durante o processamento: " + e.getMessage());
        }
    }

    /**
     * Exibe uma mensagem no console com um timestamp da data e hora atuais.
     * @param mensagem Mensagem a ser registrada no log.
     */
    private static void log(String mensagem) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter); // Obtém a data e hora atuais formatadas
        System.out.println("[" + timestamp + "] " + mensagem);
    }
}
