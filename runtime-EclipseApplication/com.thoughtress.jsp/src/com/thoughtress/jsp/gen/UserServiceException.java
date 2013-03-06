package com.thoughtress.jsp.gen;

public class UserServiceException extends Exception {
    private static final long serialVersionUID = 1L;
    public final int code;

    public UserServiceException(int code, String message) {
        super(message);
        this.code = code;
    }
}
