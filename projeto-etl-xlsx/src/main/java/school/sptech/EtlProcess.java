package school.sptech;

import school.sptech.etl.extract.XlsxExtractor;
//import school.sptech.etl.transform.DataTransformer;
import school.sptech.etl.load.DatabaseLoader;
import java.util.List;

public class EtlProcess {
    public static void main(String[] args) {
        try {
            // 1. Extração
            List<String> rawData  = XlsxExtractor.extractData("C:\\Users\\vit_o\\FlyOn\\projeto-etl-xlsx\\src\\main\\resources\\VRA_2022_01.xlsx");

            System.out.println("\nDados brutos extraídos:");
            System.out.println(rawData);


            // String natureza = extractedText.get(0);
            // extractedText.remove(0);

            // 2. Transformação

            // 3. Carga
            DatabaseLoader.loadData(rawData);



        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
