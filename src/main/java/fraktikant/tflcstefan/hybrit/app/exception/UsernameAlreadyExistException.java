package fraktikant.tflcstefan.hybrit.app.exception;

public class UsernameAlreadyExistException extends RuntimeException {
    public UsernameAlreadyExistException(String username){
        super("User with username: " + username + " already exist.");
    }
}
