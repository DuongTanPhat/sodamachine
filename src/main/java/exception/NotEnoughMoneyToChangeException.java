package exception;

public class NotEnoughMoneyToChangeException extends RuntimeException {
    private String code;
    private String msg;
    public NotEnoughMoneyToChangeException(String msg){
        this.msg=msg;
        this.code = "004";
    }

    @Override
    public String getMessage() {
        return "Exception:"+code+"\n"+msg;
    }
}
