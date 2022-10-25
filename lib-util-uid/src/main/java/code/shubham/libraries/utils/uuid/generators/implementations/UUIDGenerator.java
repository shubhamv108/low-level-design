package code.shubham.libraries.utils.uuid.generators.implementations;

import code.shubham.libraries.utils.uuid.generators.IDGenerator;

import java.util.UUID;

public class UUIDGenerator implements IDGenerator<String> {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
