package exception;

public class WrongTypeMoneyException extends RuntimeException{
    private String code;
    private String msg;
    public WrongTypeMoneyException(String msg){
        this.msg=msg;
        this.code = "001";
    }

    @Override
    public String getMessage() {
        return "Exception:"+code+"\n"+msg;
    }

}
