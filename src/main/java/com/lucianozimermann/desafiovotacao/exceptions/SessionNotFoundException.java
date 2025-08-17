package com.lucianozimermann.desafiovotacao.exceptions;

public class SessionNotFoundException extends EntityNotFoundException {
    public SessionNotFoundException() {
        super("Sessão não encontrada!");
    }
}
