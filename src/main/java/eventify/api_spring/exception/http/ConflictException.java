package eventify.api_spring.exception.http;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}