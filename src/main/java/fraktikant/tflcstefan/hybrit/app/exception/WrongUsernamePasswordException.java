package fraktikant.tflcstefan.hybrit.app.exception;

public class WrongUsernamePasswordException extends RuntimeException {
    public WrongUsernamePasswordException(){
        super("Bad Credentials!");
    }
}
