package com.lucianozimermann.desafiovotacao.exceptions;

public class InvalidAgendaException extends RuntimeException {
    public InvalidAgendaException() {
        super("O nome da Pauta não pode ser nulo!");
    }
}
