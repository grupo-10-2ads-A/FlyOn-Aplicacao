package school.sptech;

import school.sptech.etl.extract.XlsxExtractor;
import school.sptech.etl.transform.DataTransformer;
import school.sptech.etl.load.DatabaseLoader;
import school.sptech.etl.extract.S3Downloader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EtlProcess {

    private static final int TEST_LIMIT = 50;         // Limite de linhas para teste
    private static final int EXTRACT_BATCH_SIZE = 10; // Lê 10 linhas por vez
    private static final int DB_BATCH_SIZE = 10;       // Lote de inserção no banco

    public static void main(String[] args) {

        try {
            // Caminho do arquivo local
            String filePath = "projeto-etl-xlsx\\src\\main\\resources\\VRA_2022_01.xlsx";

            // FilePath do S3
//           String s3FilePath = "VRA_2022_01.xlsx";


            // Obtém InputStream diretamente do S3
//            InputStream s3InputStream = S3Downloader.getFileStream("s3-raw-flyon", s3FilePath);

            // Carrega o workbook com o InputStream S3
//          XlsxExtractor.loadWorkbook(s3InputStream);

            System.out.println("[ETL] Iniciando, arquivo recebido: " + filePath);

            try {
                int totalRows = XlsxExtractor.returnTotalRows(filePath);
                int rowsToProcess = Math.min(totalRows, TEST_LIMIT);

                System.out.println("[ETL] INICIANDO: para " + rowsToProcess + " linhas...");

                List<List<String>> batchCleanedDateTime = new ArrayList<>(DB_BATCH_SIZE);
                List<List<String>> batchCleanedData = new ArrayList<>(DB_BATCH_SIZE);
                List<Integer> batchAssentos = new ArrayList<>(DB_BATCH_SIZE);

                for (int startRow = 1; startRow <= rowsToProcess; startRow += EXTRACT_BATCH_SIZE) {
                    int currentBatchSize = Math.min(EXTRACT_BATCH_SIZE, rowsToProcess - startRow + 1);

                    // Aqui eu to chamando o extractBathData versão da EC2 sem o filePath
                    List<List<String>> batchRawData = XlsxExtractor.extractBatchData(filePath, startRow, currentBatchSize);

                    for (List<String> rawData : batchRawData) {
                        List<String> cleanedDateTime = DataTransformer.returnDateTimes(rawData);
                        int assentos_comercializados = DataTransformer.transfomDataInt(rawData.get(rawData.size() - 1));
                        List<String> cleanedData = DataTransformer.transformData(rawData);

                        // Armazena nos lotes
                        batchCleanedDateTime.add(cleanedDateTime);
                        batchCleanedData.add(cleanedData);
                        batchAssentos.add(assentos_comercializados);

                        if (batchCleanedDateTime.size() >= DB_BATCH_SIZE) {
                            DatabaseLoader.loadData(batchCleanedDateTime, batchCleanedData, batchAssentos);
                            batchCleanedDateTime.clear();
                            batchCleanedData.clear();
                            batchAssentos.clear();
                        }
                    }

                    System.out.println("[ETL LINHAS PROCESSADAS]: " + Math.min(startRow + currentBatchSize - 1, rowsToProcess) + " de " + rowsToProcess);
                }

                // Envia lotes restantes
                if (!batchCleanedDateTime.isEmpty()) {
                    DatabaseLoader.loadData(batchCleanedDateTime, batchCleanedData, batchAssentos);
                }

                // Libera recursos
                XlsxExtractor.closeWorkbook();
                System.out.println("[ETL CONCLUÍDO]");

            } catch (Exception e) {
                System.err.println("Erro no ETL:");
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("[ERRO] Falha no processo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
