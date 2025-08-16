package com.lucianozimermann.desafiovotacao.exceptions;

public class AgendaNotFoundException extends RuntimeException {

    public AgendaNotFoundException() {
        super("Pauta não encontrada!");
    }
}
