package exceptions;

public class BadFormatDate extends Exception{
    public BadFormatDate(){
        super("The String(Date) format isn't well typed");
    }
}
    
