package code.shubham.authentication.enums;

public enum SessionTimeoutType {

    YEAR("YEAR"),
    MONTH("MONTH"),
    WEEK("WEEK"),
    DAY("DAY"),
    HOUR("HOUR"),
    MINUTE("MINUTE"),
    SECOND("SECOND");

    private String sessionTimeoutType;

    SessionTimeoutType(String type){
        this.sessionTimeoutType = type;
    }

    public String getSessionTimeoutType(){
        return  this.sessionTimeoutType;
    }
}