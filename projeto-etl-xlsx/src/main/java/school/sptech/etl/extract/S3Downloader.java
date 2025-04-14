package school.sptech.etl.extract;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.ResponseTransformer;
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
        System.out.println("[DEBUG] Tentando salvar em: " + path);


        try {
            // 1. Garantir que o diretório existe
            Files.createDirectories(path.getParent());

            // 2. Deletar arquivo existente se houver
            if (Files.exists(path)) {
                System.out.println("[DEBUG] Arquivo existente encontrado, deletando...");
                Files.delete(path);
            }

            // 3. Configurar cliente S3
            S3Client s3 = S3Client.builder()
                    .region(region)
                    .credentialsProvider(DefaultCredentialsProvider.create())
                    .build();

            // 4. Fazer download com tratamento explícito de sobrescrita
            s3.getObject(
                    GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(key)
                            .build(),
                    ResponseTransformer.toFile(path)
            );

            System.out.println("[SUCESSO] Arquivo baixado para: " + path);

        } catch (S3Exception e) {
            System.err.println("[ERRO S3] " + e.awsErrorDetails().errorMessage());
            throw new RuntimeException("Falha no download do S3", e);
        } catch (FileAlreadyExistsException e) {
            System.err.println("[ERRO] Arquivo já existe e não pôde ser sobrescrito: " + path);
            throw new RuntimeException("Conflito de arquivo existente", e);
        } catch (IOException e) {
            System.err.println("[ERRO IO] " + e.getMessage());
            throw new RuntimeException("Falha ao manipular arquivo local", e);
        }
    }
}