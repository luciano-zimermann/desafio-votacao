package com.lucianozimermann.desafiovotacao.exceptions;

public class RuleConflictException extends RuntimeException {
    public RuleConflictException(String message) {
        super(message);
    }
}