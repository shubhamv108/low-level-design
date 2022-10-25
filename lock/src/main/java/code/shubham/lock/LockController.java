package code.shubham.lock;

import code.shubham.commonexceptions.InternalServerError;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class LockController {

    private final LockRepository repository;

    @Autowired
    public LockController(final LockRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<?> lock(@RequestParam("name") String name,
                                  @RequestParam("owner") String owner,
                                  @RequestParam("expiryAt") Long expireAt) {
        Date expiryAt = new Date(expireAt);
        Lock lock;
        Optional<Lock> lockOptional = this.repository.findByName(name);
        if (lockOptional.isPresent()) {
            lock = lockOptional.get();
            if (lock.getExpiryAt() != null && System.currentTimeMillis() < lock.getExpiryAt().getTime()) {
                return ResponseUtils.getErrorResponseEntity(
                        HttpStatus.LOCKED.value(),
                        String.format("%s already locked", name));
            }
        } else {
            lock = this.repository.save(
                    Lock.builder().name(name).build());
            if (lock == null)
                throw new InternalServerError(String.format("Error creating lock with name: %s", name));
        }
        int updatedRows = this.lock(owner, expiryAt, name);
        if (updatedRows == 1) {
            lockOptional = this.repository.findById(lock.getId());
            return ResponseUtils.getDataResponseEntity(
                    HttpStatus.OK.value(), lockOptional.get());
        }
        return ResponseUtils.getErrorResponseEntity(
                HttpStatus.LOCKED.value(), String.format("%s already locked", name));
    }

    @Transactional
    private int lock(String owner, Date expiryAt, String name) {
        return this.repository.lock(owner, expiryAt, name);
    }

    @PatchMapping
    public ResponseEntity<?> updateExpiry(@RequestBody code.shubham.lock.models.Lock lock) {
        new UpdateLockExpiryValidator().validateOrThrowException(lock);

        Optional<Lock> lockOptional = this.repository.findByName(lock.getName());
        if (!lockOptional.isPresent())
            throw new InvalidRequestException(
                    "name",
                    String.format("lock with name %s not present", lock.getName()));

        int updatedRows = this.repository.updateLockLease(
                new Date(lock.getExpiryAt()), lock.getName(), lock.getOwner(), lock.getVersion());
            if (updatedRows == 1)
            return ResponseUtils.getResponseEntity(HttpStatus.OK.value());
        return ResponseUtils.getErrorResponseEntity(
                HttpStatus.NOT_FOUND.value(),
                String.format("Lock not found on name: %s for owner: %s with version: %d",
                        lock.getName(), lock.getOwner(), lock.getVersion()));
    }

    @PostMapping("unlock")
    public ResponseEntity<?> unlock(@RequestBody code.shubham.lock.models.Lock lock) {
        new UnlockExpiryValidator().validateOrThrowException(lock);
        Optional<Lock> lockOptional = this.repository.findByName(lock.getName());
        if (!lockOptional.isPresent())
            throw new InvalidRequestException(
                    "name",
                    String.format("lock with name %s not present", lock.getName()));

        int updatedRows = this.repository.unlock(
                lock.getName(), lock.getOwner(), lock.getVersion());
        if (updatedRows == 1)
            return ResponseUtils.getResponseEntity(HttpStatus.OK.value());
        return ResponseUtils.getErrorResponseEntity(
                HttpStatus.NOT_FOUND.value(),
                String.format("Lock not found on name: %s for owner: %s with version: %d",
                        lock.getName(), lock.getOwner(), lock.getVersion()));
    }

    @PostMapping("expire")
    public ResponseEntity<?> expire() {
       int updatedRows = this.repository.expire();
       return ResponseUtils.getDataResponseEntity(
               HttpStatus.OK.value(),
               new HashMap<>() {{ put("Expired", updatedRows); }});
    }

}
