package school.sptech.etl.extract;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.nio.file.*;

public class S3Downloader {

    // Método responsável por baixar um arquivo do S3 para um caminho local
    public static void downloadFile(String bucketName, String key, String destinationPath) {
        // Define a região onde seu bucket está hospedado
        Region region = Region.US_EAST_1;

        // Cria e inicializa um cliente S3 com a região e credenciais do perfil local da AWS
        Path path = Paths.get(destinationPath).toAbsolutePath();
        System.out.println("[DEBUG] Caminho absoluto de destino: " + path);

        try (S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(DefaultCredentialsProvider.create()) // Usa as credencias definidas no IMA lá na instancia
                .build()) {

            // Verifica se o arquivo já existe e deleta se necessário
            if (Files.exists(path)) {
                System.out.println("Arquivo existente detectado. Deletando...");
                try {
                    Files.delete(path);
                    System.out.println("Arquivo antigo deletado com sucesso.");
                } catch (IOException e) {
                    System.err.println("Falha ao deletar arquivo existente: " + e.getMessage());
                    throw new IOException("Não foi possível remover o arquivo existente", e);
                }
            }

            // Garante que o diretório pai existe
            Files.createDirectories(path.getParent());

            // Cria uma requisição para pegar o objeto (arquivo) no bucket com o nome da chave fornecida
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName) // Nome do bucket no S3
                    .key(key)           // Nome do arquivo dentro do bucket
                    .build();

            // Executa o download do arquivo, salvando localmente no caminho especificado
            s3.getObject(getObjectRequest, path);

            // Mensagem de sucesso no console
            System.out.println("Arquivo baixado do S3 com sucesso para: " + path);

        } catch (S3Exception e) {
            // Captura e exibe erros relacionados ao S3 (como arquivo não encontrado, sem permissão, etc.)
            System.err.println("Erro ao baixar arquivo do S3: " + e.awsErrorDetails().errorMessage());
            throw new RuntimeException("Falha no download do S3", e);
        } catch (IOException e) {
            // Captura e exibe erros ao tentar deletar o arquivo existente
            System.err.println("Erro de I/O: " + e.getMessage());
            throw new RuntimeException("Falha ao manipular arquivo local", e);
        } catch (Exception e) {
            // Captura outros erros inesperados
            System.err.println("Erro inesperado: " + e.getMessage());
            throw new RuntimeException("Erro durante o download", e);
        }
    }
}