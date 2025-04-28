package school.sptech;

import school.sptech.etl.extract.ExtratorPlanilhaXlsx;
import school.sptech.etl.transform.TransformadorDados;
import school.sptech.etl.load.CarregadorDados;
import school.sptech.etl.extract.AcessoS3;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EtlProcess {

    private static final int LIMITE_TESTE = 405;         // Limite de linhas para teste
    private static final int TAMANHO_LOTE_EXTRACAO = 100; // Lê 10 linhas por vez
    private static final int TAMANHO_LOTE_BANCO = 100;       // Lote de inserção no banco
    static List<String[]> logs = new ArrayList<>();

    public static void main(String[] args) {

        try {
            // Caminho do arquivo local
//            String caminhoArquivo = "C:\\Users\\nixch\\OneDrive\\Documentos\\Github\\FlyOn-Aplicacao\\projeto-etl-xlsx\\src\\main\\resources\\VRA_2022_01.xlsx";

            // FilePath do S3
            String caminhoArquivoS3 = "VRA_2022_01.xlsx";


            // Obtém InputStream diretamente do S3
            InputStream s3InputStream = AcessoS3.obterArquivoComoStream("s3-raw-flyon", caminhoArquivoS3);

            if (s3InputStream == null) {
                ConstrutorLogs.montarLog("ERROR", "S3","Acesso ao arquivo no S3: Acess Denied");
            } else {
                ConstrutorLogs.montarLog("SUCCESS", "S3","Acesso ao arquivo no S3: Acess Success");
            }

            // Carrega o workbook com o InputStream S3
            ExtratorPlanilhaXlsx.carregarPlanilha(s3InputStream, caminhoArquivoS3);

            System.out.println("[ETL] Iniciando, arquivo recebido: " + caminhoArquivoS3);
            ConstrutorLogs.montarLog("INFO", "Main", "arquivo recebido:" + caminhoArquivoS3);

            try {
                int totalLinhas = ExtratorPlanilhaXlsx.obterTotalDeLinhas();
                int linhasParaProcessar = Math.min(totalLinhas, LIMITE_TESTE);

                System.out.println("[ETL] processando " + linhasParaProcessar + " linhas...");
                ConstrutorLogs.montarLog("INFO", "Extrator", "Processando " + linhasParaProcessar + " linhas");

                List<List<String>> loteDatasFormatadas = new ArrayList<>(TAMANHO_LOTE_BANCO);
                List<List<String>> loteDadosLimpos = new ArrayList<>(TAMANHO_LOTE_BANCO);
                List<Integer> loteAssentos = new ArrayList<>(TAMANHO_LOTE_BANCO);
                List<List<String>> loteTarifas = new ArrayList<>(TAMANHO_LOTE_BANCO);

                int linhasNaoProcessadas = 0;
                int linhasProcessadas = 0;

                for (int linhaInicial = 1; linhaInicial <= linhasParaProcessar; linhaInicial += TAMANHO_LOTE_EXTRACAO) {
                    int tamanhoLoteAtual = Math.min(TAMANHO_LOTE_EXTRACAO, linhasParaProcessar - linhaInicial + 1);

                    // Aqui eu tô chamando o extractBathData versão da EC2 sem o filePath
                    List<List<String>> loteBruto = ExtratorPlanilhaXlsx.extrairDadosEmLote(linhaInicial, tamanhoLoteAtual);

                    for (List<String> linhaBruta : loteBruto) {
                        if (linhaBruta.isEmpty()) {
                            linhasNaoProcessadas++;
                            continue;
                        }

                        if (caminhoArquivoS3.contains("VRA")) {
                            List<String> datasFormatadas = TransformadorDados.extrairETransformarDatas(linhaBruta);
                            int assentosComercializados = TransformadorDados.transformarAssentos(linhaBruta.get(linhaBruta.size() - 1));
                            List<String> dadosLimpos = TransformadorDados.removerColunasDatas(linhaBruta);

                            // Armazena nos lotes
                            loteDatasFormatadas.add(datasFormatadas);
                            loteDadosLimpos.add(dadosLimpos);
                            loteAssentos.add(assentosComercializados);

                            ConstrutorLogs.montarLog("SUCCESS", "Transformador", "Transformação bem sucedida");
                        } else {
                            loteTarifas.add(TransformadorDados.transformarTarifa(linhaBruta));
                            ConstrutorLogs.montarLog("SUCCESS", "Transformador", "Transformação bem sucedida");
                        }

                        if (loteDatasFormatadas.size() >= TAMANHO_LOTE_BANCO) {
                            if (caminhoArquivoS3.contains("VRA")) {
                                CarregadorDados.carregarStatusVoos(loteDatasFormatadas, loteDadosLimpos, loteAssentos);
                                loteDatasFormatadas.clear();
                                loteDadosLimpos.clear();
                                loteAssentos.clear();
                            }
                        }

                        if (loteTarifas.size() >= TAMANHO_LOTE_BANCO) {
                            CarregadorDados.carregarTarifas(loteTarifas);
                        }
                        linhasProcessadas++;
                    }

                    System.out.println("[ETL LINHAS PROCESSADAS]: " + linhasProcessadas + " de " + linhasParaProcessar);
                }

                ConstrutorLogs.montarLog("INFO", "Main", "Linhas processadas " + linhasParaProcessar);
                ConstrutorLogs.montarLog("INFO", "Main", "Linhas não processadas " + linhasNaoProcessadas);

                // Envia lotes restantes
                if (!loteDatasFormatadas.isEmpty()) {
                    CarregadorDados.carregarStatusVoos(loteDatasFormatadas, loteDadosLimpos, loteAssentos);
                }

                // Libera recursos
                ExtratorPlanilhaXlsx.fecharPlanilha();
                System.out.println("[ETL CONCLUÍDO]");
                ConstrutorLogs.montarLog("SUCCESS", "Main", "Processo concluído");

            } catch (Exception e) {
                System.err.println("Erro no ETL:");
                ConstrutorLogs.montarLog("ERROR", "Main", "[ETL] Erro: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.err.println("[ERRO] Falha no processo: " + e.getMessage());
            ConstrutorLogs.montarLog("ERROR", "main", "Falha no processo: " + e.getMessage());
            e.printStackTrace();
        }

        CarregadorDados.carregarRegistros(logs);
    }
}
