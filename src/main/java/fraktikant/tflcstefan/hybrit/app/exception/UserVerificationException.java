package fraktikant.tflcstefan.hybrit.app.exception;

public class UserVerificationException extends RuntimeException {
    public UserVerificationException(){
        super("Bad credentials or user account not confirmed!");
    }
}
