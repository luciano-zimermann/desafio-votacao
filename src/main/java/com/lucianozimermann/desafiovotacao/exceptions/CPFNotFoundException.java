package com.lucianozimermann.desafiovotacao.exceptions;

public class CPFNotFoundException extends EntityNotFoundException {
    public CPFNotFoundException() {
        super( "O CPF informado está inválido!");
    }
}
