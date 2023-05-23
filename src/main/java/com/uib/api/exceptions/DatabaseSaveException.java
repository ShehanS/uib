package com.uib.api.exceptions;

public class DatabaseSaveException extends Exception{
    public DatabaseSaveException(String message, Exception ex){
        super(message);
    }
}
