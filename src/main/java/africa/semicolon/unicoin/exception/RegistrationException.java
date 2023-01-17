package africa.semicolon.unicoin.exception;

public class RegistrationException extends RuntimeException {
    public RegistrationException(String emailAlreadyExists) {
        super(emailAlreadyExists);
    }
}
