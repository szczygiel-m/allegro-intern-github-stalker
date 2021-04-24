package com.szczygiel.githubstalker.exception;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException(String message) {
        super(message);
    }
}
