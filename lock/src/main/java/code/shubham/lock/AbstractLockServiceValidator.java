package code.shubham.lock;

import code.shubham.commons.util.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;

public class AbstractLockServiceValidator extends AbstractRequestValidator<code.shubham.lock.models.Lock> {
    @Override
    public IValidator<code.shubham.lock.models.Lock> validate(code.shubham.lock.models.Lock lock) {
        super.validate(lock);
        if (StringUtils.isEmpty(lock.getName()))
            this.putMessage("name", "name must not be null or empty");
        if (StringUtils.isEmpty(lock.getOwner()))
            this.putMessage("owner", "owner must not be null or empty");
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
