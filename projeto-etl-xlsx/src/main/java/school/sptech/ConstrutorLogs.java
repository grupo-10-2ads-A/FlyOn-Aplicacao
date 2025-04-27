package school.sptech;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConstrutorLogs {

    private static final DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void montarLog(String classificacao, String origem, String mensagem) {
        String dataHora = LocalDateTime.now().format(formatoDataHora);

        String[] log = new String[4];
        log[0] = (dataHora);
        log[1] = (classificacao);
        log[2] = (origem);
        log[3] = (mensagem);

        EtlProcess.logs.add(log);
    }
}
