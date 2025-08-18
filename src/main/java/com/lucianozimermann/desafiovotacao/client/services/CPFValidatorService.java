package com.lucianozimermann.desafiovotacao.client.services;

import com.lucianozimermann.desafiovotacao.client.enums.CPFVoteStatus;
import com.lucianozimermann.desafiovotacao.exceptions.CPFNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class CPFValidatorService {
    public CPFVoteStatus validateCPF(String cpf){
        if (!isValidCPF(cpf)) {
            throw new CPFNotFoundException();
        }

        boolean canVote = ThreadLocalRandom.current().nextBoolean();

        return canVote ? CPFVoteStatus.ABLE_TO_VOTE : CPFVoteStatus.UNABLE_TO_VOTE;
    }

    /**
     * Para esta validação, foi considerada a seguinte lógica:
     *  - O CPF é válido caso tenha 11 dígitos e caso não tenha todos os dígitos iguais
     */
    public boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        char[] digits = cpf.toCharArray();
        char first = digits[0];

        for (char d : digits) {
            if (d != first) {
                return true;
            }
        }

        return false;
    }

}
