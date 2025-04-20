package school.sptech.etl.extract;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class XlsxExtractor {

    private static Workbook cachedWorkbook = null;
    private static String cachedFilePath = null;
    private static boolean usandoStream = false;

    // Carregando a planilha direto do S3
//    public static void loadWorkbook(InputStream inputStream) throws IOException {
//        closeWorkbook();
//        cachedWorkbook = new XSSFWorkbook(inputStream);
//        usandoStream = true;
//    }

    // Versão para testar localmente
    private static void initWorkbookCache(String filePath) throws IOException {
        if (!filePath.equals(cachedFilePath)) {
            closeWorkbook();
            cachedWorkbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
            cachedFilePath = filePath;
            usandoStream = false;
        }
    }

    // Versão para testar na EC2
//    public static Integer returnTotalRows() {
//        if (cachedWorkbook == null) {
//            throw new IllegalStateException("O workbook não foi carregado.");
//        }
//        Sheet sheet = cachedWorkbook.getSheetAt(0);
//        return sheet.getLastRowNum();
//    }

    // Versão para testar localmente
    public static Integer returnTotalRows(String filePath) throws IOException {
        if (!usandoStream) initWorkbookCache(filePath);
        Sheet sheet = cachedWorkbook.getSheetAt(0);
        return sheet.getLastRowNum();
    }

    //     Versão para testar localmente
    public static List<String> extractData(String filePath, int currentRow) throws IOException {
        if (!usandoStream) initWorkbookCache(filePath);
        Sheet sheet = cachedWorkbook.getSheetAt(0);
        Row row = sheet.getRow(currentRow);
        List<String> res = new ArrayList<>();

        if (row != null) {
            if (filePath.contains("VRA")) {
                if (row.getCell(8).getStringCellValue().contains("BRASIL") && row.getCell(12).getStringCellValue().contains("BRASIL")) {
                    row.getCell(6).setCellType(CellType.STRING);

                    String data_hora_partida_prevista = formatarDataHoraCelula(row.getCell(9));
                    String data_hora_partida_real = formatarDataHoraCelula(row.getCell(10));
                    String data_hora_chegada_prevista = formatarDataHoraCelula(row.getCell(13));
                    String data_hora_chegada_real = formatarDataHoraCelula(row.getCell(14));
                    String sigla_empresa_aerea = formatarSeNulo(row.getCell(0));
                    String empresa_aerea = formatarSeNulo(row.getCell(1));
                    String origem = formatarSeNulo(row.getCell(8));
                    String destino = formatarSeNulo(row.getCell(12));
                    String situacao_voo = formatarSeNulo(row.getCell(15));
                    String situacao_partida = formatarSeNulo(row.getCell(18));
                    String situacao_chegada = formatarSeNulo(row.getCell(19));
                    String assentos_comercializados = formatarSeNulo(row.getCell(6));

                    Collections.addAll(res,
                            data_hora_partida_prevista,
                            data_hora_partida_real,
                            data_hora_chegada_prevista,
                            data_hora_chegada_real,
                            sigla_empresa_aerea,
                            empresa_aerea,
                            origem,
                            destino,
                            situacao_voo,
                            situacao_partida,
                            situacao_chegada,
                            assentos_comercializados
                    );
                }
            } else {
                String ano = String.valueOf(row.getCell(0).getNumericCellValue());
                String mes = String.valueOf(row.getCell(1).getNumericCellValue());
                String sigla_empresa_aerea = formatarSeNulo(row.getCell(2));
                String sigla_origem = formatarSeNulo(row.getCell(3));
                String sigla_destino = formatarSeNulo(row.getCell(4));
                String tarifa = String.valueOf(row.getCell(5).getNumericCellValue());

                Collections.addAll(res,
                        ano,
                        mes,
                        sigla_empresa_aerea,
                        sigla_origem,
                        sigla_destino,
                        tarifa
                );
            }


        }
        return res;
    }

    // Versão para usar na EC2
//    public static List<List<String>> extractBatchData(int startRow, int batchSize) {
//        if (cachedWorkbook == null) {
//            throw new IllegalStateException("O workbook não foi carregado.");
//        }
//
//        Sheet sheet = cachedWorkbook.getSheetAt(0);
//        List<List<String>> batchData = new ArrayList<>(batchSize);
//        int endRow = Math.min(startRow + batchSize - 1, sheet.getLastRowNum());
//
//        for (int rowNum = startRow; rowNum <= endRow; rowNum++) {
//            Row row = sheet.getRow(rowNum);
//            List<String> res = new ArrayList<>();
//
//            if (row != null) {
//                Cell cell6 = row.getCell(6);
//                if (cell6 != null) {
//                    cell6.setCellType(CellType.STRING);
//                }
//
//                String data_hora_partida_prevista = formatarDataHoraCelula(row.getCell(9));
//                String data_hora_partida_real = formatarDataHoraCelula(row.getCell(10));
//                String data_hora_chegada_prevista = formatarDataHoraCelula(row.getCell(13));
//                String data_hora_chegada_real = formatarDataHoraCelula(row.getCell(14));
//                String sigla_empresa_aerea = formatarSeNulo(row.getCell(0));
//                String empresa_aerea = formatarSeNulo(row.getCell(1));
//                String origem = formatarSeNulo(row.getCell(8));
//                String destino = formatarSeNulo(row.getCell(12));
//                String situacao_voo = formatarSeNulo(row.getCell(15));
//                String situacao_partida = formatarSeNulo(row.getCell(18));
//                String situacao_chegada = formatarSeNulo(row.getCell(19));
//                String assentos_comercializados = formatarSeNulo(row.getCell(6));
//
//                Collections.addAll(res,
//                        data_hora_partida_prevista,
//                        data_hora_partida_real,
//                        data_hora_chegada_prevista,
//                        data_hora_chegada_real,
//                        sigla_empresa_aerea,
//                        empresa_aerea,
//                        origem,
//                        destino,
//                        situacao_voo,
//                        situacao_partida,
//                        situacao_chegada,
//                        assentos_comercializados
//                );
//            }
//
//            batchData.add(res);
//        }
//
//        return batchData;
//    }

    // Versão para testar localmente
    public static List<List<String>> extractBatchData(String filePath, int startRow, int batchSize) throws IOException {
        if (!usandoStream) initWorkbookCache(filePath);
        Sheet sheet = cachedWorkbook.getSheetAt(0);
        List<List<String>> batchData = new ArrayList<>(batchSize);

        int endRow = Math.min(startRow + batchSize - 1, sheet.getLastRowNum());

        for (int rowNum = startRow; rowNum <= endRow; rowNum++) {
            batchData.add(extractData(filePath, rowNum));
        }

        return batchData;
    }

    public static void closeWorkbook() throws IOException {
        if (cachedWorkbook != null) {
            cachedWorkbook.close();
            cachedWorkbook = null;
            cachedFilePath = null;
            usandoStream = false;
        }
    }

    private static String formatarSeNulo(Cell cell) {
        if (cell == null) return null;
        return cell.getStringCellValue();
    }

    private static String formatarDataHoraCelula(Cell cell) {
        if (cell == null) return null;

        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return dateFormat.format(cell.getDateCellValue());
        }
        return cell.toString();
    }
}
