package com.lucianozimermann.desafiovotacao.exceptions;

public class SessionNotFoundException extends RuntimeException{

    public SessionNotFoundException() {
        super("Sessão não encontrada!");
    }
}
