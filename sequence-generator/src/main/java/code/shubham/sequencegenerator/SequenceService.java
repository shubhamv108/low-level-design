package code.shubham.sequencegenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SequenceService {

    private final AcceptedUserRepository acceptedUserRepository;
    private final SequenceUserRepository sequenceUserRepository;


    @Autowired
    public SequenceService(
            AcceptedUserRepository acceptedUserRepository,
            SequenceUserRepository sequenceUserRepository) {
        this.acceptedUserRepository = acceptedUserRepository;
        this.sequenceUserRepository = sequenceUserRepository;
    }

    public AcceptedUser accept(Integer userId) {
        return this.acceptedUserRepository.save(AcceptedUser.builder().userId(userId).build());
    }

    public SequenceUser create(Integer userId) {
        return this.sequenceUserRepository.save(SequenceUser.builder().userId(userId).build());
    }

    public int acceptToFirstEmpty(Integer userId) {
        return this.acceptedUserRepository.acceptToFirstEmpty(userId);
    }
}
