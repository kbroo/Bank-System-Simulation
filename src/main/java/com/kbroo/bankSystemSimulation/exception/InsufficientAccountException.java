package com.kbroo.bankSystemSimulation.exception;

public class InsufficientAccountException extends RuntimeException{
    public InsufficientAccountException(String message) {
        super(message);
    }
}
