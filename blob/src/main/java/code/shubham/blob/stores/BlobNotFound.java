package code.shubham.blob.stores;

public class BlobNotFound extends RuntimeException {
    private Integer id;
    public BlobNotFound(Integer id) {
        super("No blob found ith id: " + id);
    }
}
