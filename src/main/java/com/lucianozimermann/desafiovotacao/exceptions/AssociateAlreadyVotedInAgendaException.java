package com.lucianozimermann.desafiovotacao.exceptions;

public class AssociateAlreadyVotedInAgendaException extends RuleConflictException{
    public AssociateAlreadyVotedInAgendaException() {
        super("Este associado já votou nessa Pauta!");
    }
}
