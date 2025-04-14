package school.sptech.etl.extract;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class S3Downloader {

    // Método responsável por baixar um arquivo do S3 para um caminho local
    public static void downloadFile(String bucketName, String key, String destinationPath) {
        // Define a região onde seu bucket está hospedado
        Region region = Region.US_EAST_1;

        // Cria e inicializa um cliente S3 com a região e credenciais do perfil local da AWS
        try (S3Client s3 = S3Client.builder()
                .region(region)
                .credentialsProvider(DefaultCredentialsProvider.create()) // Usa as credencias definidas no IMA lá na instancia.
                .build()) {

            // Deleta o arquivo local caso ele já exista
            Path path = Paths.get(destinationPath);
            if (Files.exists(path)) {
                Files.delete(path);
            }

            // Cria uma requisição para pegar o objeto (arquivo) no bucket com o nome da chave fornecida
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName) // Nome do bucket no S3
                    .key(key)           // Nome do arquivo dentro do bucket (ex: pasta/arquivo.xlsx)
                    .build();

            // Executa o download do arquivo, salvando localmente no caminho especificado
            s3.getObject(getObjectRequest, path);

            // Mensagem de sucesso no console
            System.out.println("Arquivo baixado do S3 com sucesso para: " + destinationPath);

        } catch (S3Exception e) {
            // Captura e exibe erros relacionados ao S3 (como arquivo não encontrado, sem permissão, etc.)
            System.err.println("Erro ao baixar arquivo do S3: " + e.awsErrorDetails().errorMessage());
        } catch (IOException e) {
            // Captura e exibe erros ao tentar deletar o arquivo existente
            System.err.println("Erro ao deletar arquivo existente: " + e.getMessage());
        }
    }
}
