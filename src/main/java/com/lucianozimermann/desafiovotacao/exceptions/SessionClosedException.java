package com.lucianozimermann.desafiovotacao.exceptions;

public class SessionClosedException extends RuleConflictException{
    public SessionClosedException() {
        super("A sessão de votação está encerrada!");
    }
}
