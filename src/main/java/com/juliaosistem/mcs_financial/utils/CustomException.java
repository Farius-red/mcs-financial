package com.juliaosistem.mcs_financial.utils;

public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String errorCode;

    public CustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
