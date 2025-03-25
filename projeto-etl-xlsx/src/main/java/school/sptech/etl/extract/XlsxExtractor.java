package school.sptech.etl.extract;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.text.SimpleDateFormat;

public class XlsxExtractor {

    public static List<String> extractData(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);  // Assumindo que os dados estão na primeira aba

        Iterator<Row> rowIterator = sheet.iterator();
        List<String> res = new ArrayList();

        // Isso é a condição do while para pegar o arquivo completo: rowIterator.hasNext()
        // "int i" é só para rodar o while controladamente
        int i = 0;
        while (i < 2) {
            Row row = rowIterator.next();

            // A primeira linha pode conter os cabeçalhos
            if (row.getRowNum() == 0) continue;

            row.getCell(6).setCellType(CellType.STRING);

            String data_hora_partida_prevista = formatarDataHoraCelula(row.getCell(9));
            String data_hora_partida_real = formatarDataHoraCelula(row.getCell(10));
            String data_hora_chegada_prevista = formatarDataHoraCelula(row.getCell(13));
            String data_hora_chegada_real = formatarDataHoraCelula(row.getCell(14));
//            String sigla_empresa_aerea = row.getCell(0).getStringCellValue();
//            String empresa_aerea = row.getCell(1).getStringCellValue();
//            String origem = row.getCell(8).getStringCellValue();
//            String destino = row.getCell(12).getStringCellValue();
//            String situacao_voo = row.getCell(15).getStringCellValue();
//            String situacao_partida = row.getCell(18).getStringCellValue();
//            String situacao_chegada = row.getCell(19).getStringCellValue();
            String assentos_comercializados = row.getCell(6).getStringCellValue();

            res.add(data_hora_partida_prevista);
            res.add(data_hora_partida_real);
            res.add(data_hora_chegada_prevista);
            res.add(data_hora_chegada_real);
//            res.add(sigla_empresa_aerea);
//            res.add(empresa_aerea);
//            res.add(origem);
//            res.add(destino);
//            res.add(situacao_voo);
//            res.add(situacao_partida);
//            res.add(situacao_chegada);
            res.add(assentos_comercializados);

            i++;
        }
        System.out.println("passou");
        workbook.close();
        file.close();

        return res;
    }

    public static String formatarDataHoraCelula(Cell cell) {
        if (cell == null) {
            return null; // ou null, conforme sua necessidade
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
