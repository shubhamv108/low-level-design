package code.shubham.models.authentication;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class CreateAccount {

    @Getter
    public static class Request {
        private String userName;
        private String password;
    }

    @Builder
    @Data
    public static class Response {
        String username;
    }
}
