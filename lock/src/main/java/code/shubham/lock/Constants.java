package code.shubham.lock;

public interface Constants {

    interface Queries {
        String LOCK = "UPDATE locks SET owner = ?, expiry_at = ?, version = version + 1 WHERE name = ? AND (expiry_at is null OR expiry_at < NOW())";
        String UPDATE_EXPIRY = "UPDATE locks SET expiry_at = ? WHERE name = ? AND owner = ? AND version = ?";
        String UNLOCK = "UPDATE locks SET owner = null, expiry_at = null, version = version + 1 WHERE name = ? AND owner = ? AND version = ?";
        String EXPIRE = "UPDATE locks SET owner = null, expiry_at = null, version = version + 1 WHERE expiry_at < NOW()";
    }

}
