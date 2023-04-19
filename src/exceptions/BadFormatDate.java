package exceptions;

public class BadFormatDate extends RuntimeException{
    public BadFormatDate(){
        super("The String(Date) format isn't well typed");
    }
}
    
