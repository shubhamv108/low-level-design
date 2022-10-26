package code.shubham.models.authentication;


import lombok.Getter;

public class UpdateAccount {

    @Getter
    public static class Request {
        Integer userId;
    }
}
