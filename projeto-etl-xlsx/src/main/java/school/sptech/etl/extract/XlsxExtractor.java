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

public class XlsxExtractor {

    public static List<String> extractData(String filePath) throws IOException {
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);  // Assumindo que os dados estão na primeira aba

        Iterator<Row> rowIterator = sheet.iterator();
        List<String> res = new ArrayList();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            // A primeira linha pode conter os cabeçalhos
            if (row.getRowNum() == 1) continue;

            String data_hora_partida_prevista = String.valueOf(row.getCell(9).getNumericCellValue());
            String data_hora_partida_real = String.valueOf(row.getCell(10).getNumericCellValue());
            String data_hora_chegada_prevista = String.valueOf(row.getCell(13).getNumericCellValue());
            String data_hora_chegada_real = String.valueOf(row.getCell(14).getNumericCellValue());
            String sigla_empresa_aerea = row.getCell(0).getStringCellValue();
            String empresa_aerea = row.getCell(1).getStringCellValue();
            String origem = row.getCell(8).getStringCellValue();
            String destino = row.getCell(12).getStringCellValue();
            String situacao_voo = row.getCell(15).getStringCellValue();
            String situacao_partida = row.getCell(18).getStringCellValue();
            String situacao_chegada = row.getCell(19).getStringCellValue();
            String assentos_comercializados = row.getCell(6).getStringCellValue();

            res.add(data_hora_partida_prevista);
            res.add(data_hora_partida_real);
            res.add(data_hora_chegada_prevista);
            res.add(data_hora_chegada_real);
            res.add(sigla_empresa_aerea);
            res.add(empresa_aerea);
            res.add(origem);
            res.add(destino);
            res.add(situacao_voo);
            res.add(situacao_partida);
            res.add(situacao_chegada);
            res.add(assentos_comercializados);


            System.out.println("passou");
        }
        workbook.close();
        file.close();

        return res;
    }
}
