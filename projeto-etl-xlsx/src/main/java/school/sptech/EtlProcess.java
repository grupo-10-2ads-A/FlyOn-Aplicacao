package school.sptech;

import school.sptech.etl.extract.XlsxExtractor;
//import school.sptech.etl.transform.DataTransformer;
import school.sptech.etl.load.DatabaseLoader;

import java.util.ArrayList;
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
            System.out.println("\nDados inseridos no banco");
            DatabaseLoader.loadData(rawData);



<<<<<<< HEAD
=======
//            System.out.println(extractedText);

            List<String> dateTimes = new ArrayList<>();
            dateTimes.add(extractedText.get(0));
            dateTimes.add(extractedText.get(1));
            dateTimes.add(extractedText.get(2));
            dateTimes.add(extractedText.get(3));

            extractedText.remove(3);
            extractedText.remove(2);
            extractedText.remove(1);
            extractedText.remove(0);


            // 2. Transformação
            int cleanedInt = DataTransformer.transfomDataInt(extractedText.get(extractedText.size() - 1));
            extractedText.remove(extractedText.size() - 1);
//            System.out.println(cleanedInt);
            List<String> cleanedTextDateTime = DataTransformer.transformDataDateTime(dateTimes);
//            System.out.println(cleanedTextDateTime);

            // 3. Carga
//            DatabaseLoader.loadData(cleanedTextDateTime, extractedText, cleanedInt);
>>>>>>> origin/dev-nicolas

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
