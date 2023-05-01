package com.example.universityschedule.exception;

public class EntityNotUpdatedException extends RuntimeException {
    public EntityNotUpdatedException(String message) {
        super(message);
    }
}
