package code.shubham.pastebin;

import code.shubham.blobmodels.CreateBlobRequest;
import code.shubham.blobmodels.CreateBlobResponse;
import code.shubham.blobmodels.GetDownloadSignedUrlRequest;
import code.shubham.client.blob.BlobClient;
import code.shubham.client.keygeneration.KeyGenerationClient;
import code.shubham.commonexceptions.InvalidRequestException;
import code.shubham.commons.util.StringUtils;
import code.shubham.models.paste.CreatePasteRequest;
import code.shubham.models.paste.CreatePasteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PasteService {

    private final PasteRepository repository;

    private final KeyGenerationClient keyGenerationClient;
    private final BlobClient blobClient;

    @Value("${service.blob.host: 127.0.0.1}")
    private String blobServiceHost;

    @Value("${service.blob.port: 8092}")
    private String blobServicePort;

    @Autowired
    public PasteService(PasteRepository repository,
                        KeyGenerationClient keyGenerationClient,
                        BlobClient blobClient) {
        this.repository = repository;
        this.keyGenerationClient = keyGenerationClient;
        this.blobClient = blobClient;
    }

    public CreatePasteResponse create(CreatePasteRequest createPasteRequest, Integer userId) {
        String key = this.keyGenerationClient.poll();
        String shortUrl = String.format("https://%s:%s/%s", blobServiceHost, blobServicePort, key);
        CreateBlobRequest createBlobRequest = CreateBlobRequest.builder().
                ownerService("Paste").
                checksum(createPasteRequest.getChecksum()).
                build();
        CreateBlobResponse createBlobResponse = this.blobClient.create(createBlobRequest, userId);
        Paste paste = Paste.builder().
                key(key).
                blobId(createBlobResponse.getId()).
                userId(userId).
                expiryAt(new Date(
                        System.currentTimeMillis() + createPasteRequest.getTimeToLiveInMilliSeconds())).
                build();
        paste = this.repository.save(paste);
        return CreatePasteResponse.builder().
                shortUrl(shortUrl).
                uploadUrl(createBlobResponse.getUploadSignedUrlResponse().getUrl()).
                uploadUrlExpiryAt(createBlobResponse.getUploadSignedUrlResponse().getExpiryAt()).
                build();
    }

    public String get(String key, String userId) {
        if (StringUtils.isEmpty(key))
            throw new InvalidRequestException("key", "key must not null or empty");
        Optional<Paste> paste = this.repository.findByKey(key);
        if (paste.isEmpty())
            throw new InvalidRequestException("key", "Invalid paste key");

        GetDownloadSignedUrlRequest getDownloadSignedUrlRequest = GetDownloadSignedUrlRequest.builder().
                blobId(paste.get().getBlobId()).
                build();
        this.blobClient.getById(getDownloadSignedUrlRequest, userId);
    }
}
