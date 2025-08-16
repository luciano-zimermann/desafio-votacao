package com.lucianozimermann.desafiovotacao.exceptions;

public class SessionAlreadyOpenException extends RuntimeException {

    public SessionAlreadyOpenException() {
        super("Já existe uma Sessão aberta para essa Pauta!");
    }
}
