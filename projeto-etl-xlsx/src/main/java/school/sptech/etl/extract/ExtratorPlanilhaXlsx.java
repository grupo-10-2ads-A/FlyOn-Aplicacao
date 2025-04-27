package school.sptech.etl.extract;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExtratorPlanilhaXlsx {

    private static Workbook planilhaEmCache = null;
    private static String caminhoArquivoEmCache = null;
    private static boolean usandoStream = false;

    // Carregando a planilha direto do S3
//    public static void carregarPlanilha(InputStream inputStream) throws IOException {
//        fecharPlanilha();
//        planilhaEmCache = new XSSFWorkbook(inputStream);
//        usandoStream = true;
//    }

    // Versão para testar localmente
    private static void inicializarCache(String caminhoArquivo) throws IOException {
        if (!caminhoArquivo.equals(caminhoArquivoEmCache)) {
            fecharPlanilha();
            planilhaEmCache = new XSSFWorkbook(new FileInputStream(new File(caminhoArquivo)));
            caminhoArquivoEmCache = caminhoArquivo;
            usandoStream = false;
        }
    }

    // Versão para testar na EC2
//    public static Integer obterTotalDeLinhas() {
//        if (planilhaEmCache == null) {
//            throw new IllegalStateException("O workbook não foi carregado.");
//        }
//        Sheet sheet = planilhaEmCache.getSheetAt(0);
//        return sheet.getLastRowNum();
//    }

    // Versão para testar localmente
    public static Integer obterTotalDeLinhas(String caminhoArquivo) throws IOException {
        if (!usandoStream) inicializarCache(caminhoArquivo);
        Sheet aba = planilhaEmCache.getSheetAt(0);
        return aba.getLastRowNum();
    }

    //     Versão para testar localmente
    public static List<String> extrairDadosDaLinha(String caminhoArquivo, int numeroLinha) throws IOException {
        if (!usandoStream) inicializarCache(caminhoArquivo);
        Sheet aba = planilhaEmCache.getSheetAt(0);
        Row linha = aba.getRow(numeroLinha);
        List<String> dados = new ArrayList<>();

        if (linha != null) {
            if (caminhoArquivo.contains("VRA")) {
                if (linha.getCell(8).getStringCellValue().contains("BRASIL") && linha.getCell(12).getStringCellValue().contains("BRASIL")) {
                    linha.getCell(6).setCellType(CellType.STRING);

                    String partidaPrevista = formatarDataOuTexto(linha.getCell(9));
                    String partidaReal = formatarDataOuTexto(linha.getCell(10));
                    String chegadaPrevista = formatarDataOuTexto(linha.getCell(13));
                    String chegadaReal = formatarDataOuTexto(linha.getCell(14));
                    String siglaEmpresa = formatarDataOuTexto(linha.getCell(0));
                    String nomeEmpresa = formatarDataOuTexto(linha.getCell(1));
                    String sigla_origem = formatarDataOuTexto(linha.getCell(7));
                    String origem = formatarDataOuTexto(linha.getCell(8));
                    String sigla_destino = formatarDataOuTexto(linha.getCell(11));
                    String destino = formatarDataOuTexto(linha.getCell(12));
                    String situacaoVoo = formatarDataOuTexto(linha.getCell(15));
                    String situacaoPartida = formatarDataOuTexto(linha.getCell(18));
                    String situacaoChegada = formatarDataOuTexto(linha.getCell(19));
                    String assentosVendidos = formatarDataOuTexto(linha.getCell(6));

                    Collections.addAll(dados,
                            partidaPrevista,
                            partidaReal,
                            chegadaPrevista,
                            chegadaReal,
                            siglaEmpresa,
                            nomeEmpresa,
                            sigla_origem,
                            origem,
                            sigla_destino,
                            destino,
                            situacaoVoo,
                            situacaoPartida,
                            situacaoChegada,
                            assentosVendidos
                    );
                }
            } else {
                String ano = String.valueOf((int) linha.getCell(0).getNumericCellValue());
                String mes = String.valueOf((int) linha.getCell(1).getNumericCellValue());
                String siglaEmpresa = formatarDataOuTexto(linha.getCell(2));
                String origem = formatarDataOuTexto(linha.getCell(3));
                String destino = formatarDataOuTexto(linha.getCell(4));
                String tarifa = String.valueOf(linha.getCell(5).getNumericCellValue());

                Collections.addAll(dados,
                        ano,
                        mes,
                        siglaEmpresa,
                        origem,
                        destino,
                        tarifa
                );
            }
        }
        return dados;
    }

    // Versão para usar na EC2
//    public static List<List<String>> extrairDadosEmLote(int linhaInicial, int quantidade) {
//        if (planilhaEmCache == null) {
//            throw new IllegalStateException("A planilha não foi carregada.");
//        }
//
//        Sheet aba = planilhaEmCache.getSheetAt(0);
//        List<List<String>> loteDeDados = new ArrayList<>(quantidade);
//        int linhaFinal = Math.min(linhaInicial + quantidade - 1, aba.getLastRowNum());
//
//        for (int numeroLinha = linhaInicial; numeroLinha <= linhaFinal; numeroLinha++) {
//            Row linha = aba.getRow(numeroLinha);
//            List<String> dados = new ArrayList<>();
//
//            if (linha != null) {
//                Cell celulaAssentos = linha.getCell(6);
//                if (celulaAssentos != null) {
//                    celulaAssentos.setCellType(CellType.STRING);
//                }
//
//                String partidaPrevista = formatarDataOuTexto(linha.getCell(9));
//                String partidaReal = formatarDataOuTexto(linha.getCell(10));
//                String chegadaPrevista = formatarDataOuTexto(linha.getCell(13));
//                String chegadaReal = formatarDataOuTexto(linha.getCell(14));
//                String siglaEmpresa = formatarTexto(linha.getCell(0));
//                String nomeEmpresa = formatarTexto(linha.getCell(1));
//                String origem = formatarTexto(linha.getCell(8));
//                String destino = formatarTexto(linha.getCell(12));
//                String situacaoVoo = formatarTexto(linha.getCell(15));
//                String situacaoPartida = formatarTexto(linha.getCell(18));
//                String situacaoChegada = formatarTexto(linha.getCell(19));
//                String assentosVendidos = formatarTexto(linha.getCell(6));
//
//                Collections.addAll(dados,
//                        partidaPrevista,
//                        partidaReal,
//                        chegadaPrevista,
//                        chegadaReal,
//                        siglaEmpresa,
//                        nomeEmpresa,
//                        origem,
//                        destino,
//                        situacaoVoo,
//                        situacaoPartida,
//                        situacaoChegada,
//                        assentosVendidos
//                );
//            }
//
//            loteDeDados.add(dados);
//        }
//
//        return loteDeDados;
//    }

    // Versão para testar localmente
    public static List<List<String>> extrairDadosEmLote(String caminhoArquivo, int linhaInicial, int quantidade) throws IOException {
        if (!usandoStream) inicializarCache(caminhoArquivo);
        Sheet aba = planilhaEmCache.getSheetAt(0);
        List<List<String>> loteDeDados = new ArrayList<>(quantidade);

        int linhaFinal = Math.min(linhaInicial + quantidade - 1, aba.getLastRowNum());

        for (int numeroLinha = linhaInicial; numeroLinha <= linhaFinal; numeroLinha++) {
            loteDeDados.add(extrairDadosDaLinha(caminhoArquivo, numeroLinha));
        }

        return loteDeDados;
    }

    public static void fecharPlanilha() throws IOException {
        if (planilhaEmCache != null) {
            planilhaEmCache.close();
            planilhaEmCache = null;
            caminhoArquivoEmCache = null;
            usandoStream = false;
        }
    }

    private static String formatarDataOuTexto(Cell celula) {
        if (celula == null) return null;

        if (celula.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(celula)) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return formato.format(celula.getDateCellValue());
        }
        return celula.toString();
    }
}
