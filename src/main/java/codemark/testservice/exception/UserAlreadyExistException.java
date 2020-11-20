package codemark.testservice.exception;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
