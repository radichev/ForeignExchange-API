package com.radichev.foreignexchange.exception;

public class ApiCallException extends RuntimeException {
    private int code;
    private String body;

    public ApiCallException(int code, String body) {
        super(code + " " + body);
        this.code = code;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public String getBody() {
        return body;
    }
}
