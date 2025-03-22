package school.sptech.etl.extract;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
            if (row.getRowNum() == 0) continue;

            String natureza = row.getCell(0).getStringCellValue();
            String janeiro = row.getCell(1).getStringCellValue();
            String fevereiro = row.getCell(2).getStringCellValue();
            String marco = row.getCell(3).getStringCellValue();
            String abril = row.getCell(4).getStringCellValue();
            String maio = row.getCell(5).getStringCellValue();
            String junho = row.getCell(6).getStringCellValue();
            String julho = row.getCell(7).getStringCellValue();
            String agosto = row.getCell(8).getStringCellValue();
            String setembro = row.getCell(9).getStringCellValue();
            String outubro = row.getCell(10).getStringCellValue();
            String novembro = row.getCell(11).getStringCellValue();
            String dezembro = row.getCell(12).getStringCellValue();
            String total = row.getCell(13).getStringCellValue();

            res.add(natureza);
            res.add(janeiro);
            res.add(fevereiro);
            res.add(marco);
            res.add(abril);
            res.add(maio);
            res.add(junho);
            res.add(julho);
            res.add(agosto);
            res.add(setembro);
            res.add(outubro);
            res.add(novembro);
            res.add(dezembro);
            res.add(total);

            System.out.println("passou");
        }
        workbook.close();
        file.close();

        return res;
    }
}
