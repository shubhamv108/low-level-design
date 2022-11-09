package code.shubham.models.ratelimit.rules;

import code.shubham.common.models.ModelsUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class GetRule {

    @Getter
    public class Request {

        @Builder
        public record QueryParams(Plan plan, String apiName, String role) {
            @Override
            public String toString() {
                return ModelsUtils.queryString(this);
            }
        }
        public class Header {
            public Integer userId;
        }
        public class Body {

        }
    }

    @Getter
    @Setter
    public class Response {
        public class Header {

        }
        public class Body {
            Rule rule;
        }
    }
}
