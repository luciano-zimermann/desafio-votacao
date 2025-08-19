package com.lucianozimermann.desafiovotacao.api.v1.repositories;

import com.lucianozimermann.desafiovotacao.api.v1.entities.Agenda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class AgendaRepositoryTest {
    private static final String AGENDA_NAME = "Nova Pauta";
    private static final String AGENDA_DESCRIPTION = "Descrição da Pauta";

    @Autowired
    private AgendaRepository agendaRepository;

    @Test
    @DisplayName("Deve salvar e buscar uma pauta pelo ID")
    void saveAndFindById_shouldReturnAgenda() {
        Agenda agenda = buildAgenda();

        Agenda saved = agendaRepository.save(agenda);

        Optional<Agenda> found = agendaRepository.findById(saved.getId());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(AGENDA_NAME, found.get().getName());
        Assertions.assertEquals(AGENDA_DESCRIPTION, found.get().getDescription());
    }

    private Agenda buildAgenda() {
        return Agenda.builder()
                     .name(AGENDA_NAME)
                     .description(AGENDA_DESCRIPTION)
                     .build();
    }
}
