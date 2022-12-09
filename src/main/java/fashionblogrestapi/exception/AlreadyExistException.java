package fashionblogrestapi.exception;

public class AlreadyExistException extends RuntimeException{
    private final String message;

    public AlreadyExistException(){
        super("Not found");
        this.message = "Not found";
    }

    public AlreadyExistException(String msg){
        super(msg);
        this.message = msg;
    }
}
