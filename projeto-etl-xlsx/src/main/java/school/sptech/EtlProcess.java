package school.sptech;

import school.sptech.etl.extract.XlsxExtractor;
import school.sptech.etl.transform.DataTransformer;
import school.sptech.etl.load.DatabaseLoader;

import java.util.ArrayList;
import java.util.List;

public class EtlProcess {
    public static void main(String[] args) {
        try {
            // 1. Extração
            List<String> extractedText = XlsxExtractor.extractData("C:\\Users\\nixch\\OneDrive\\Documentos\\Github\\FlyOn\\projeto-etl-xlsx\\src\\main\\resources\\VRA_2022_01.xlsx");

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
