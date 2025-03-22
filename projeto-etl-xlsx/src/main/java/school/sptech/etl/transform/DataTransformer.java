package school.sptech.etl.transform;

import java.util.ArrayList;
import java.util.List;

public class DataTransformer {

    public static List<Integer> transformData(List<String> numeros) {
        // Aqui você pode aplicar regras de transformação, se necessário.
        // Por exemplo: converter valores, formatar "strings", ou aplicar validações.
        List<Integer> dados = new ArrayList<>();

        for (String numero : numeros) {

            try {
                if(numero.contains(".")) {
                    numero = numero.replace(".", "");
                }
                dados.add(Integer.parseInt(numero));
            } catch (NumberFormatException e) {
                System.err.println("Erro ao converter '" + numero + "' para inteiro. Ignorando esse valor.");
            }
        }

        System.out.println("Passei no DataTransformer");
        System.out.println(dados);
        return dados;
    }
}