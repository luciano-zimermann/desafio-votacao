package com.lucianozimermann.desafiovotacao.exceptions;

public class AgendaNotFoundException extends EntityNotFoundException {
    public AgendaNotFoundException() {
        super("Pauta não encontrada!");
    }
}
