package fashionblogrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleResourceNotFoundException(NotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), null, HttpStatus.NOT_FOUND.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<String> handleBlogApiException(AppException exception){
        return new ResponseEntity<>(exception.getMessage(), null, HttpStatus.BAD_REQUEST.value());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistException.class)
    @ResponseBody
    public ResponseEntity<String> handleAlreadyExistException(AlreadyExistException exception){
        return new ResponseEntity<>(exception.getMessage(), null, HttpStatus.CONFLICT.value());
    }

}

