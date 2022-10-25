package code.shubham.lock;

import code.shubham.commons.validators.IValidator;

public class UpdateLockExpiryValidator extends AbstractLockServiceValidator {
    @Override
    public IValidator<code.shubham.lock.models.Lock> validate(code.shubham.lock.models.Lock lock) {
        super.validate(lock);
        this.validateLockExpireAt(lock);
        this.validateLockVersion(lock);
        return this;
    }

    public void validateLockExpireAt(code.shubham.lock.models.Lock lock) {
        if (lock.getExpiryAt() == null)
            this.putMessage("expireAt", "expireAt must not be null or empty");
    }

    public void validateLockVersion(code.shubham.lock.models.Lock lock) {
        if (lock.getVersion() == null)
            this.putMessage("version", "version must not be null or empty");
    }
}
