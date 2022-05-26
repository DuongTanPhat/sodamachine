package exception;

public class UserNotEnoughMoneyException extends RuntimeException{
    private String code;
    private String msg;
    public UserNotEnoughMoneyException(String msg){
        this.msg=msg;
        this.code = "002";
    }

    @Override
    public String getMessage() {
        return "Exception:"+code+"\n"+msg;
    }
}
