package org.example.exceptions;

public class MaisDeUmRegistroException extends Exception{

    public MaisDeUmRegistroException(String msg) {
        super(msg);
    }

    public MaisDeUmRegistroException(String msg, Throwable e) {
        super(msg, e);
    }
}
