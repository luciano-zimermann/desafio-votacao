package com.lucianozimermann.desafiovotacao.exceptions;

public class SessionAlreadyOpenException extends RuleConflictException {
    public SessionAlreadyOpenException() {
        super("Já existe uma Sessão aberta para essa Pauta!");
    }
}
