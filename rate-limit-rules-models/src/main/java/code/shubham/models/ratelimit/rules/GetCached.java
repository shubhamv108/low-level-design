package code.shubham.models.ratelimit.rules;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class GetCached {
    public class Request {

    }

    public class Response {
        @Builder
        @Getter
        public static class Body {
            public List<Rule> rules;
        }
    }
}
