package eventify.api_spring.exception.http;

public class NoContentException extends RuntimeException {
    public NoContentException(String message) {
        super(message);
    }
}
