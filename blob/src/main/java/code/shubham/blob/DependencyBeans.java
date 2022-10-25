package code.shubham.blob;

import code.shubham.blob.stores.AWSS3BlobStore;
import code.shubham.blob.stores.IBlobStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;

@Configuration
public class DependencyBeans {

    @Value("${blobstore: aws}")
    private String blobStore;

    @Value("${aws.accesskey}")
    private String awsAccessKey;

    @Value("${aws.secretAccess}")
    private String awsSecretAccessKey;

    public AwsCredentials getAwsCredentials() {
        return new AwsCredentials() {
            @Override
            public String accessKeyId() {
                return awsAccessKey;
            }

            @Override
            public String secretAccessKey() {
                return awsSecretAccessKey;
            }
        };
    }

    @Autowired
    @Bean
    public IBlobStore getBlobStore(AwsCredentials credentials) {
        return new AWSS3BlobStore(credentials);
    }

}
