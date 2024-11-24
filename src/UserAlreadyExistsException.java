public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super("User already exists");
    }
}
