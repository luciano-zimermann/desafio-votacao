package com.lucianozimermann.desafiovotacao.services;

import com.lucianozimermann.desafiovotacao.entities.Agenda;
import com.lucianozimermann.desafiovotacao.exceptions.AgendaNotFoundException;
import com.lucianozimermann.desafiovotacao.repositories.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository repository;

    public Agenda findById(Long id) {
        Agenda agenda = repository.findById(id)
                                  .orElseThrow(AgendaNotFoundException::new);

        return agenda;
    }
}
