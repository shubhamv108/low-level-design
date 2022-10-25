package code.shubham.models.authentication;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Collection;

@Builder
@Data
public class Login {

    @Getter
    public class Request {
        private String username;
        private String password;
    }

    @Builder
    @Data
    public class Response {
        private String utid;
        private String jti;
        private String accessToken;
        private String username;
        private Collection<String> roles;
    }
}
