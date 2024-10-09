package isf.sc.utils;

public class SessionInvalidException extends Exception {

    String message;

    public SessionInvalidException() {
        message = "";
        message = "SessionInvalid";
    }

    public SessionInvalidException(String msg) {
        message = "";
        message = msg;
    }
}
