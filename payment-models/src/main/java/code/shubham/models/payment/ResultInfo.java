package code.shubham.models.payment;

public class ResultInfo {
    ResultStatus status;
    String code;
    String message;
}

enum ResultStatus {
    SUCCESS
}
