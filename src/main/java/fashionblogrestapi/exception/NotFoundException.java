package fashionblogrestapi.exception;

public class NotFoundException extends RuntimeException{
    private final String message;

    public NotFoundException(){
        super("Not found");
        this.message = "Not found";
    }

    public NotFoundException(String msg){
        super(msg);
        this.message = msg;
    }
}

