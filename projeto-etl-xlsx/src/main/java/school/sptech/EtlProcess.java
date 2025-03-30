package school.sptech;

import school.sptech.etl.extract.XlsxExtractor;
import school.sptech.etl.transform.DataTransformer;
import school.sptech.etl.load.DatabaseLoader;

import java.util.List;

public class EtlProcess {
    public static void main(String[] args) {
        try {
            // 1. Extração
            List<String> rawData = XlsxExtractor.extractData("C:\\Users\\nixch\\OneDrive\\Documentos\\Github\\FlyOn\\projeto-etl-xlsx\\src\\main\\resources\\VRA_2022_01.xlsx");

            // 2. Transformação
            List<String> cleanedDateTime = DataTransformer.returnDateTimes(rawData);
//            System.out.println(cleanedDateTime);
            List<String> cleanedData = DataTransformer.transformData(rawData);
            int assentos_comercializados = DataTransformer.transfomDataInt(rawData.get(rawData.size() - 1));
            rawData.remove(rawData.size() - 1);
//            System.out.println(cleanedInt);

            // 3. Carga
            DatabaseLoader.loadData(cleanedDateTime, cleanedData, assentos_comercializados);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
