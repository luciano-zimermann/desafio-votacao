package com.lucianozimermann.desafiovotacao.api.v1.services;

import com.lucianozimermann.desafiovotacao.ap1.v1.dto.requests.AgendaDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.AgendaResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Agenda;
import com.lucianozimermann.desafiovotacao.ap1.v1.services.AgendaService;
import com.lucianozimermann.desafiovotacao.exceptions.InvalidAgendaException;
import com.lucianozimermann.desafiovotacao.ap1.v1.repositories.AgendaRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class AgendaServiceTest {
    private static final String AGENDA_TITLE = "Nova Pauta";
    private static final String AGENDA_DESCRIPTION = "Descrição da Pauta";

    @Mock
    private AgendaRepository repository;

    @InjectMocks
    private AgendaService service;

    @Test
    @DisplayName("Deve criar a pauta corretamente com os dados válidos")
    void createAgenda_shouldRegisterAndReturnAgenda() {
        Agenda agendaMock = buildAgenda();
        AgendaDTO agendaDTO = new AgendaDTO(AGENDA_TITLE, AGENDA_DESCRIPTION);

        Mockito.when(repository.save( Mockito.any( Agenda.class))).thenReturn(agendaMock);

        AgendaResponseDTO agendaResponseDTO = service.register(agendaDTO);

        Assertions.assertNotNull(agendaResponseDTO);
        Assertions.assertEquals(agendaMock.getId(), agendaResponseDTO.getId());
        Assertions.assertEquals(agendaMock.getName(), agendaResponseDTO.getName());
        Assertions.assertEquals(agendaMock.getDescription(), agendaResponseDTO.getDescription());

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Agenda.class));
    }

    @Test
    @DisplayName("Deve retornar exceção se o nome da Pauta for nulo")
    void createAgenda_shouldThrowInvalidAgendaExceptionWhenHasInvalidData() {
        AgendaDTO invalidDTO = new AgendaDTO(null, AGENDA_DESCRIPTION);

        Assertions.assertThrows(InvalidAgendaException.class, () -> service.register(invalidDTO));

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Agenda.class));
    }

    private Agenda buildAgenda() {
        return Agenda.builder()
                     .id(1L)
                     .name(AGENDA_TITLE)
                     .description(AGENDA_DESCRIPTION)
                     .build();
    }
}
