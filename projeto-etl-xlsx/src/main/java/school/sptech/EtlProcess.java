package school.sptech;

import school.sptech.etl.extract.XlsxExtractor;
import school.sptech.etl.transform.DataTransformer;
import school.sptech.etl.load.DatabaseLoader;
import java.util.List;

public class EtlProcess {
    public static void main(String[] args) {
        try {
            // 1. Extração
            List<String> extractedText = XlsxExtractor.extractData("C:\\Users\\nixch\\OneDrive\\Documentos\\Github\\projeto-etl-xlsx\\src\\main\\resources\\roubo_de_carga(Criminal)-EstadoSP.xlsx");

            String natureza = extractedText.get(0);
            extractedText.remove(0);

            // 2. Transformação
             List<Integer> cleanedText = DataTransformer.transformData(extractedText);

            // 3. Carga
            DatabaseLoader.loadData(natureza, cleanedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
