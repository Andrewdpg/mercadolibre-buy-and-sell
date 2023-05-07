package exceptions;

public class NotNumberNegative extends RuntimeException{
    public NotNumberNegative(){
        super("Negative number not expected");
    }

    public NotNumberNegative(String msg) {
        super(msg);
    }
}
