package org.example.exceptions;

public class TableException extends Exception{

    public TableException(String msg) {
        super(msg);
    }

    public TableException(String msg, Throwable e) {
        super(msg, e);
    }
}
