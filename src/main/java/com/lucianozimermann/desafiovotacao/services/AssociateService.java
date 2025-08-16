package com.lucianozimermann.desafiovotacao.services;

import com.lucianozimermann.desafiovotacao.entities.Associate;
import com.lucianozimermann.desafiovotacao.exceptions.AssociateNotFoundException;
import com.lucianozimermann.desafiovotacao.repositories.AssociateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssociateService {

    @Autowired
    private AssociateRepository repository;

    public Associate findById(Long id) {
        Associate associate = repository.findById(id)
                                        .orElseThrow(AssociateNotFoundException::new);

        return associate;
    }
}
