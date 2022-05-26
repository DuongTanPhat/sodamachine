package exception;

public class SoldOutException extends RuntimeException{
    private String code;
    private String msg;
    public SoldOutException(String msg){
        this.msg=msg;
        this.code = "003";
    }

    @Override
    public String getMessage() {
        return "Exception:"+code+"\n"+msg;
    }
}
