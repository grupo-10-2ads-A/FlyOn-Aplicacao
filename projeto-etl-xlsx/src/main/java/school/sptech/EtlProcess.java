package school.sptech;

import school.sptech.etl.extract.ExtratorPlanilhaXlsx;
import school.sptech.etl.transform.TransformadorDados;
import school.sptech.etl.load.PersistenciaDados;

import java.util.ArrayList;
import java.util.List;

public class EtlProcess {

    private static final int LIMITE_TESTE = 405;         // Limite de linhas para teste
    private static final int TAMANHO_LOTE_EXTRACAO = 100; // Lê 10 linhas por vez
    private static final int TAMANHO_LOTE_BANCO = 100;       // Lote de inserção no banco

    public static void main(String[] args) {

        try {
            // Caminho do arquivo local
            String caminhoArquivo = "C:\\Users\\nixch\\OneDrive\\Documentos\\Github\\FlyOn-Aplicacao\\projeto-etl-xlsx\\src\\main\\resources\\VRA_2022_01.xlsx";

            // FilePath do S3
//           String caminhoArquivoS3 = "VRA_2022_01.xlsx";


            // Obtém InputStream diretamente do S3
//            InputStream s3InputStream = S3Acess.getFileStream("s3-raw-flyon", s3FilePath);

            // Carrega o workbook com o InputStream S3
//          XlsxExtractor.loadWorkbook(s3InputStream);

            System.out.println("[ETL] Iniciando, arquivo recebido: " + caminhoArquivo);

            try {
                int totalLinhas = ExtratorPlanilhaXlsx.obterTotalDeLinhas(caminhoArquivo);
                int linhasParaProcessar = Math.min(totalLinhas, LIMITE_TESTE);

                System.out.println("[ETL] INICIANDO: para " + linhasParaProcessar + " linhas...");

                List<List<String>> loteDatasFormatadas = new ArrayList<>(TAMANHO_LOTE_BANCO);
                List<List<String>> loteDadosLimpos = new ArrayList<>(TAMANHO_LOTE_BANCO);
                List<Integer> loteAssentos = new ArrayList<>(TAMANHO_LOTE_BANCO);
                List<List<String>> loteTarifas = new ArrayList<>(TAMANHO_LOTE_BANCO);

                for (int linhaInicial = 1; linhaInicial <= linhasParaProcessar; linhaInicial += TAMANHO_LOTE_EXTRACAO) {
                    int tamanhoLoteAtual = Math.min(TAMANHO_LOTE_EXTRACAO, linhasParaProcessar - linhaInicial + 1);

                    // Aqui eu tô chamando o extractBathData versão da EC2 sem o filePath
                    List<List<String>> loteBruto = ExtratorPlanilhaXlsx.extrairDadosEmLote(caminhoArquivo, linhaInicial, tamanhoLoteAtual);

                    boolean loteContemVazios = false;
                    for (List<String> linhaBruta : loteBruto) {
                        if (linhaBruta.isEmpty()) {
                            loteContemVazios = true;
                            break;
                        }
                    }

                    if (loteContemVazios) {
                        continue;
                    }

                    for (List<String> linhaBruta : loteBruto) {
                        if (caminhoArquivo.contains("VRA")) {
                            List<String> datasFormatadas = TransformadorDados.extrairETransformarDatas(linhaBruta);
                            int assentosComercializados = TransformadorDados.transformarAssentos(linhaBruta.get(linhaBruta.size() - 1));
                            List<String> dadosLimpos = TransformadorDados.removerColunasDatas(linhaBruta);

                            // Armazena nos lotes
                            loteDatasFormatadas.add(datasFormatadas);
                            loteDadosLimpos.add(dadosLimpos);
                            loteAssentos.add(assentosComercializados);
                        } else {
                            loteTarifas.add(TransformadorDados.transformarTarifa(linhaBruta));
                        }

                        if (loteDatasFormatadas.size() >= TAMANHO_LOTE_BANCO) {
                            if (caminhoArquivo.contains("VRA")) {
                                PersistenciaDados.carregarStatusVoos(loteDatasFormatadas, loteDadosLimpos, loteAssentos);
                                loteDatasFormatadas.clear();
                                loteDadosLimpos.clear();
                                loteAssentos.clear();
                            }
                        }

                        if (loteTarifas.size() >= TAMANHO_LOTE_BANCO) {
                            PersistenciaDados.carregarTarifas(loteTarifas);
                        }
                    }

                    System.out.println("[ETL LINHAS PROCESSADAS]: " + Math.min(linhaInicial + linhaInicial - 1, linhasParaProcessar) + " de " + linhasParaProcessar);
                }

                // Envia lotes restantes
                if (!loteDatasFormatadas.isEmpty()) {
                    PersistenciaDados.carregarStatusVoos(loteDatasFormatadas, loteDadosLimpos, loteAssentos);
                }

                // Libera recursos
                ExtratorPlanilhaXlsx.fecharPlanilha();
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
