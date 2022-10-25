package code.shubham.commons.exceptions;

import code.shubham.common.exceptions.ClientException;

public class InvalidCommandException extends ClientException {
    public InvalidCommandException(final Object command, final String... message) {
        super(String.format("Invalid command %s, %s", command, message));
    }
}
