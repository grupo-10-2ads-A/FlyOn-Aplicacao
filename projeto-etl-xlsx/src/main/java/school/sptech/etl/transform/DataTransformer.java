package school.sptech.etl.transform;

import java.util.ArrayList;
import java.util.List;

public class DataTransformer {

    public static List<String> transformDataDateTime(List<String> dateTimes) {
        String data_hora_partida_prevista = dateTimes.get(0);
        String data_hora_partida_real = dateTimes.get(1);
        String data_hora_chegada_prevista = dateTimes.get(2);
        String data_hora_chegada_real = dateTimes.get(3);

        String data_hora_partida_prevista_formatada =
                data_hora_partida_prevista.substring(6, 10) + "-" + data_hora_partida_prevista.substring(3, 6) + data_hora_partida_prevista.substring(0, 2) + data_hora_partida_prevista.substring(10);
        String data_hora_partida_real_formatada =
                data_hora_partida_real.substring(6, 10) + "-" + data_hora_partida_real.substring(3, 6) + data_hora_partida_real.substring(0, 2) + data_hora_partida_real.substring(10);
        String data_hora_chegada_prevista_formatada =
                data_hora_chegada_prevista.substring(6, 10) + "-" + data_hora_chegada_prevista.substring(3, 6) + data_hora_chegada_prevista.substring(0, 2) + data_hora_chegada_prevista.substring(10);
        String data_hora_chegada_real_formatada =
                data_hora_chegada_real.substring(6, 10) + "-" + data_hora_chegada_real.substring(3, 6) + data_hora_chegada_real.substring(0, 2) + data_hora_chegada_real.substring(10);

        data_hora_partida_prevista_formatada = data_hora_partida_prevista_formatada.replaceAll("/", "-");
        data_hora_partida_real_formatada = data_hora_partida_real_formatada.replaceAll("/", "-");
        data_hora_chegada_prevista_formatada = data_hora_chegada_prevista_formatada.replaceAll("/", "-");
        data_hora_chegada_real_formatada = data_hora_chegada_real_formatada.replaceAll("/", "-");

        List<String> dateTimesFormat = new ArrayList<>();
        dateTimesFormat.add(data_hora_partida_prevista_formatada);
        dateTimesFormat.add(data_hora_partida_real_formatada);
        dateTimesFormat.add(data_hora_chegada_prevista_formatada);
        dateTimesFormat.add(data_hora_chegada_real_formatada);

        System.out.println("Success - Transformação da data e hora bem sucedidas");
        return dateTimesFormat;
    }

    public static Integer transfomDataInt(String assentos_comercializados) {
        System.out.println("Success - Transformação do número de assentos bem sucedida");
        return Integer.parseInt(assentos_comercializados);
    }
}