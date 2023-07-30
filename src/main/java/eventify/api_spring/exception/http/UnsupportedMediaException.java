package eventify.api_spring.exception.http;

public class UnsupportedMediaException extends RuntimeException {
    public UnsupportedMediaException(String message) {
        super(message);
    }
}