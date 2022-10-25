package code.shubham.lock;

import code.shubham.commons.validators.IValidator;

public class UnlockExpiryValidator extends AbstractLockServiceValidator {
    @Override
    public IValidator<code.shubham.lock.models.Lock> validate(code.shubham.lock.models.Lock lock) {
        super.validate(lock);
        this.validateLockVersion(lock);
        return this;
    }
}
