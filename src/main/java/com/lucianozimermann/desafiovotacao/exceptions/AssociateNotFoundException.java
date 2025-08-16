package com.lucianozimermann.desafiovotacao.exceptions;

public class AssociateNotFoundException extends RuntimeException {

    public AssociateNotFoundException() {
        super("Associado não encontrado!");
    }
}
