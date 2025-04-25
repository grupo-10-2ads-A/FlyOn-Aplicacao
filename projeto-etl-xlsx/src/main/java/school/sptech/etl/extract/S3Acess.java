package school.sptech.etl.extract;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.InputStream;

public class S3Acess {

    /*
     * Retorna um InputStream do arquivo no S3, sem salvar localmente.
     */
    public static InputStream getFileStream(String bucketName, String key) {
        Region region = Region.US_EAST_1;

        try {
            // Inicializa o cliente S3
            S3Client s3 = S3Client.builder()
                    .region(region)
                    .credentialsProvider(DefaultCredentialsProvider.create())
                    .build();

            // Cria a requisição
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            // Retorna o stream do arquivo
            ResponseInputStream<GetObjectResponse> s3InputStream = s3.getObject(
                    getObjectRequest,
                    ResponseTransformer.toInputStream()
            );

            System.out.println("Stream do arquivo obtido com sucesso do S3.");
            return s3InputStream;

        } catch (S3Exception e) {
            System.err.println("Erro ao acessar arquivo no S3: " + e.awsErrorDetails().errorMessage());
            return null;
        }
    }
}
