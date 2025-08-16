package com.lucianozimermann.desafiovotacao.services;

import com.lucianozimermann.desafiovotacao.dto.requests.AgendaDTO;
import com.lucianozimermann.desafiovotacao.dto.responses.AgendaResponseDTO;
import com.lucianozimermann.desafiovotacao.entities.Agenda;
import com.lucianozimermann.desafiovotacao.exceptions.AgendaNotFoundException;
import com.lucianozimermann.desafiovotacao.repositories.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository repository;

    public AgendaResponseDTO register( AgendaDTO dto) {
        Agenda agenda = Agenda.builder()
                              .name(dto.getName())
                              .description(dto.getDescription())
                              .build();

        agenda = repository.save(agenda);

        return AgendaResponseDTO.builder()
                                .id(agenda.getId())
                                .name(agenda.getName())
                                .description(agenda.getDescription())
                                .build();
    }

    public Agenda findById(Long id) {
        Agenda agenda = repository.findById(id)
                                  .orElseThrow(AgendaNotFoundException::new);

        return agenda;
    }
}
