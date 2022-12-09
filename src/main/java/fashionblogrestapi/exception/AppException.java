package fashionblogrestapi.exception;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public AppException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public AppException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public AppException(String s) {
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

