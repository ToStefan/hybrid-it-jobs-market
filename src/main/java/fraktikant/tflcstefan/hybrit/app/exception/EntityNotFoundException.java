package fraktikant.tflcstefan.hybrit.app.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Long id){
        super("Entity with identifier: " + id + " not found!");
    }
}
