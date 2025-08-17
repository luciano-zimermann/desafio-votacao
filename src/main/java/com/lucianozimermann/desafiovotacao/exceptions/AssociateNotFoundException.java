package com.lucianozimermann.desafiovotacao.exceptions;

public class AssociateNotFoundException extends EntityNotFoundException {
    public AssociateNotFoundException() {
        super("Associado não encontrado!");
    }
}
