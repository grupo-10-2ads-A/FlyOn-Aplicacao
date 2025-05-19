package school.sptech.etl.extract;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.text.SimpleDateFormat;

public class XlsxExtractor {

    public static Integer returnTotalRows(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);  // Assumindo que os dados estão na primeira aba

        Iterator<Row> rowIterator = sheet.iterator();
        int totalRow = 0;

        while (rowIterator.hasNext()) {
            rowIterator.next();
            totalRow++;
        }

        workbook.close();
        file.close();

        return totalRow;
    }

    public static List<String> extractData(String filePath, int currentRow) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);  // Assumindo que os dados estão na primeira aba

        List<String> res = new ArrayList();
        Row row = sheet.getRow(currentRow);

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

        workbook.close();
        file.close();

        return res;
    }

    private static String formatarSeNulo(Cell cell) {
        if (cell == null) {
            return null;
        }

        return cell.getStringCellValue();
    }

    public static String formatarDataHoraCelula(Cell cell) {
        if (cell == null) {
            return null;
        }

        // Verifica se a célula contém uma data
        if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
            // Cria um formatador de data
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            // Obtém o valor da data e formata
            Date data = cell.getDateCellValue();
            return dateFormat.format(data);
        }

        // Se não for uma data, retorna o valor como string
        return cell.toString();
    }
}
