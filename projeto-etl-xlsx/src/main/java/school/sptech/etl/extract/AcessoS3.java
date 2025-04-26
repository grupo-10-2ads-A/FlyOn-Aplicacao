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

public class AcessoS3 {

    /*
     * Retorna um InputStream do arquivo no S3, sem salvar localmente.
     */
    public static InputStream obterArquivoComoStream(String nomeDoBucket, String caminhoDoArquivo) {
        Region regiao = Region.US_EAST_1;

        try {
            // Inicializa o cliente S3
            S3Client clienteS3 = S3Client.builder()
                    .region(regiao)
                    .credentialsProvider(DefaultCredentialsProvider.create())
                    .build();

            // Cria a requisição
            GetObjectRequest requisicao = GetObjectRequest.builder()
                    .bucket(nomeDoBucket)
                    .key(caminhoDoArquivo)
                    .build();

            // Retorna o stream do arquivo
            ResponseInputStream<GetObjectResponse> streamDeEntrada = clienteS3.getObject(
                    requisicao,
                    ResponseTransformer.toInputStream()
            );

            System.out.println("Stream do arquivo obtido com sucesso do S3.");
            return streamDeEntrada;

        } catch (S3Exception e) {
            System.err.println("Erro ao acessar arquivo no S3: " + e.awsErrorDetails().errorMessage());
            return null;
        }
    }
}
