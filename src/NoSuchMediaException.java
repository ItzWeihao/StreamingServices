public class NoSuchMediaException extends Exception {
    public NoSuchMediaException(String message) {
        super("Your search gave no results");
    }
}