package com.lucianozimermann.desafiovotacao.api.v1.services;

import com.lucianozimermann.desafiovotacao.api.v1.dto.requests.AgendaDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.AgendaResponseDTO;
import com.lucianozimermann.desafiovotacao.api.v1.entities.Agenda;
import com.lucianozimermann.desafiovotacao.exceptions.AgendaNotFoundException;
import com.lucianozimermann.desafiovotacao.exceptions.InvalidAgendaException;
import com.lucianozimermann.desafiovotacao.api.v1.repositories.AgendaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AgendaService {

    @Autowired
    private AgendaRepository repository;

    public AgendaResponseDTO register(AgendaDTO dto) {
        log.info("Registrando uma nova Pauta: name='{}', description='{}'", dto.getName(), dto.getDescription());

        Agenda agenda = Agenda.builder()
                              .name(dto.getName())
                              .description(dto.getDescription())
                              .build();

        if (agenda.getName() == null) {
            log.warn("Tentativa de registrar Pauta inválida: nome nulo");

            throw new InvalidAgendaException();
        }

        agenda = repository.save(agenda);

        log.info("Pauta registrada com sucesso: id={}", agenda.getId());

        return buildAgendaResponseDTO(agenda);
    }

    public AgendaResponseDTO findById(Long id) {
        log.info("Buscando Pauta pelo id={}", id);

        Agenda agenda = repository.findById(id)
                                  .orElseThrow(() -> {
                                      log.warn("Pauta não encontrada para id={}", id);
                                      return new AgendaNotFoundException();
                                  });

        log.debug("Pauta encontrada: id={}, name='{}'", agenda.getId(), agenda.getName());

        return buildAgendaResponseDTO(agenda);
    }

    private AgendaResponseDTO buildAgendaResponseDTO(Agenda agenda) {
        log.trace("Construindo DTO para a Pauta id={}", agenda.getId());

        return AgendaResponseDTO.builder()
                                .id(agenda.getId())
                                .name(agenda.getName())
                                .description(agenda.getDescription())
                                .build();
    }
}
