package com.szczygiel.githubstalker.exception;

public class RequestTimeoutException extends RuntimeException {
    public RequestTimeoutException(String message) {
        super(message);
    }
}
