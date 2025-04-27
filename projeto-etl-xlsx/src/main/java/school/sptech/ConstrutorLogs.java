package school.sptech;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConstrutorLogs {

    private static final DateTimeFormatter formatoDataHora = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void MontarLog(String classificacao, String mensagem) {
        String dataHora = LocalDateTime.now().format(formatoDataHora);

        String[] log = new String[3];
        log[0] = (dataHora);
        log[1] = (classificacao);
        log[2] = (mensagem);

        EtlProcess.logs.add(log);
    }
}
