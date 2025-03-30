package school.sptech;

import school.sptech.etl.extract.XlsxExtractor;
import school.sptech.etl.transform.DataTransformer;
import school.sptech.etl.load.DatabaseLoader;

import java.util.List;

public class EtlProcess {
    public static void main(String[] args) {
        try {
            int totalRows = XlsxExtractor.returnTotalRows("C:\\Users\\vit_o\\FlyOn\\projeto-etl-xlsx\\src\\main\\resources\\VRA_2022_01.xlsx");
            int countRow = 0;
            for (int row = 1; row <= totalRows; row++) {
                countRow++;

                // 1. Extração
                List<String> rawData = XlsxExtractor.extractData("C:\\Users\\vit_o\\FlyOn\\projeto-etl-xlsx\\src\\main\\resources\\VRA_2022_01.xlsx", row);
//                System.out.println("Success - Extração bem sucedida");
                // 2. Transformação
                List<String> cleanedDateTime = DataTransformer.returnDateTimes(rawData);
//            System.out.println(cleanedDateTime);
                int assentos_comercializados = DataTransformer.transfomDataInt(rawData.get(rawData.size() - 1));
                List<String> cleanedData = DataTransformer.transformData(rawData);
//            System.out.println(cleanedInt);

                // 3. Carga
                DatabaseLoader.loadData(cleanedDateTime, cleanedData, assentos_comercializados);
                System.out.println("Success - Linha " + row + " inserida");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
