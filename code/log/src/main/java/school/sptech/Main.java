package school.sptech;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        log("Iniciando o sistema de análise de voos...");

        receberDados();
        analisarDados();
        limparDados();
        enviarParaDashboard();

        log("Processamento concluído com sucesso!");
    }

    private static void receberDados() {
        log("Recebendo dados de voos domésticos...");
        simularProcessamento(2000);
        log("Dados recebidos: 50.000 registros carregados.");
    }

    private static void analisarDados() {
        log("Analisando os dados recebidos...");
        simularProcessamento(3000);
        int errosDetectados = new Random().nextInt(500);
        log("Análise concluída: " + errosDetectados + " registros inconsistentes encontrados.");
    }

    private static void limparDados() {
        log("Limpando os dados inconsistentes...");
        simularProcessamento(2500);
        log("Processo de limpeza concluído. Dados prontos para envio.");
    }

    private static void enviarParaDashboard() {
        log("Enviando dados limpos para o dashboard...");
        simularProcessamento(1500);
        log("Envio finalizado: 49.500 registros disponíveis para visualização.");
    }

    private static void simularProcessamento(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            log("Erro durante o processamento: " + e.getMessage());
        }
    }

    private static void log(String mensagem) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println("[" + timestamp + "] " + mensagem);
    }
}
