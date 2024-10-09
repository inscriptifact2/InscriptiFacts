package com.isf.app.exception;

public class NoDataFoundException extends Exception {

    public NoDataFoundException() {
        super("No Records Fetched");
    }

    public NoDataFoundException(String s) {
        super(s);
    }
}
