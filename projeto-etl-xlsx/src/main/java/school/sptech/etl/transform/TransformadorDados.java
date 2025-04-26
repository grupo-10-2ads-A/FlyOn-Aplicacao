package school.sptech.etl.transform;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransformadorDados {


    /**
     * Extrai e transforma as 4 primeiras colunas de data/hora.
     */
    public static List<String> extrairETransformarDatas(List<String> dadosBrutos) {
        List<String> datas = new ArrayList<>();

        datas.add(dadosBrutos.get(0));
        datas.add(dadosBrutos.get(1));
        datas.add(dadosBrutos.get(2));
        datas.add(dadosBrutos.get(3));

        return transformarDatas(datas);
    }

    /**
     * Transforma datas do formato dd/MM/yyyy HH:mm para yyyy-MM-dd HH:mm.
     */
    public static List<String> transformarDatas(List<String> datasHoras) {
        if (datasHoras == null || datasHoras.size() != 4) {
            throw new IllegalArgumentException("Lista deve conter exatamente 4 elementos");
        }

        List<String> datasFormatadas = new ArrayList<>();
        DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter formatoSaida = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (String dataHora : datasHoras) {
            if (dataHora == null || dataHora.trim().isEmpty()) {
                datasFormatadas.add(null);
                continue;
            }
            LocalDateTime dataConvertida = LocalDateTime.parse(dataHora, formatoEntrada);
            datasFormatadas.add(dataConvertida.format(formatoSaida));
        }

        System.out.println("Sucesso - Transformação das datas e horas concluída");
        return datasFormatadas;
    }

    /**
     * Remove os dados de data/hora do início da lista.
     */
    public static List<String> removerColunasDatas(List<String> dados) {
        dados.remove(3);
        dados.remove(2);
        dados.remove(1);
        dados.remove(0);

        return dados;
    }

    /**
     * Substitui vírgulas por pontos no último item da lista (tarifa).
     */
    public static List<String> transformarTarifa(List<String> dados) {
        int indiceUltimo = dados.size() - 1;
        String tarifa = dados.get(indiceUltimo).replace(",", ".");
        dados.set(indiceUltimo, tarifa);
        System.out.println("Success - Transformação das vírgulas em pontos bem sucedida");
        return dados;
    }

    /**
     * Converte texto para número de assentos.
     */
    public static Integer transformarAssentos(String assentos) {
        System.out.println("Success - Transformação do número de assentos bem sucedida");
        return Integer.parseInt(assentos);
    }
}