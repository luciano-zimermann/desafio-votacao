package com.lucianozimermann.desafiovotacao.exceptions;

public class AgendaWithoutClosedSessionsException extends RuleConflictException{
    public AgendaWithoutClosedSessionsException() {
        super("Não existem Sessões de votação nesta Pauta!");
    }
}
