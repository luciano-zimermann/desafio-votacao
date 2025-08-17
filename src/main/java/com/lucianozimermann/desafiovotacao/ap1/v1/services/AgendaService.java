package com.lucianozimermann.desafiovotacao.ap1.v1.services;

import com.lucianozimermann.desafiovotacao.ap1.v1.dto.requests.AgendaDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.AgendaResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Agenda;
import com.lucianozimermann.desafiovotacao.exceptions.AgendaNotFoundException;
import com.lucianozimermann.desafiovotacao.exceptions.InvalidAgendaException;
import com.lucianozimermann.desafiovotacao.ap1.v1.repositories.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository repository;

    public AgendaResponseDTO register(AgendaDTO dto) {
        Agenda agenda = Agenda.builder()
                              .name(dto.getName())
                              .description(dto.getDescription())
                              .build();

        if (agenda.getName() == null) {
            throw new InvalidAgendaException();
        }

        agenda = repository.save(agenda);

        return buildAgendaResponseDTO(agenda);
    }

    public AgendaResponseDTO findById(Long id) {
        Agenda agenda = repository.findById(id)
                                  .orElseThrow(AgendaNotFoundException::new);

        return buildAgendaResponseDTO(agenda);
    }

    private AgendaResponseDTO buildAgendaResponseDTO(Agenda agenda) {
        return AgendaResponseDTO.builder()
                                .id(agenda.getId())
                                .name(agenda.getName())
                                .description(agenda.getDescription())
                                .build();
    }
}
