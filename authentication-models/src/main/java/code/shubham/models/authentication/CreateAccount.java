package code.shubham.models.authentication;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class CreateAccount {

    @Getter
    public class Request {
        private String userName;
        private String password;
    }

    @Builder
    @Data
    public class Response {
        String username;
    }
}
