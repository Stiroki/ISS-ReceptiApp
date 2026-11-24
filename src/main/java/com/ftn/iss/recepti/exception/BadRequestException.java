package com.ftn.iss.recepti.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) { super(message); }
}