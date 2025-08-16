package com.lucianozimermann.desafiovotacao.services;

import com.lucianozimermann.desafiovotacao.dto.requests.AgendaDTO;
import com.lucianozimermann.desafiovotacao.dto.responses.AgendaResponseDTO;
import com.lucianozimermann.desafiovotacao.entities.Agenda;
import com.lucianozimermann.desafiovotacao.exceptions.InvalidAgendaException;
import com.lucianozimermann.desafiovotacao.repositories.AgendaRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


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

        when(repository.save(any(Agenda.class))).thenReturn(agendaMock);

        AgendaResponseDTO agendaResponseDTO = service.register(agendaDTO);

        assertNotNull(agendaResponseDTO);
        assertEquals(agendaMock.getId(), agendaResponseDTO.getId());
        assertEquals(agendaMock.getName(), agendaResponseDTO.getName());
        assertEquals(agendaMock.getDescription(), agendaResponseDTO.getDescription());

        verify(repository, times(1)).save(any(Agenda.class));
    }

    @Test
    @DisplayName("Deve retornar exceção se o nome da Pauta for nulo")
    void createAgenda_shouldThrowInvalidAgendaExceptionWhenHasInvalidData() {
        AgendaDTO invalidDTO = new AgendaDTO(null, AGENDA_DESCRIPTION);

        assertThrows(InvalidAgendaException.class, () -> service.register(invalidDTO));

        verify(repository, never()).save(any(Agenda.class));
    }

    private Agenda buildAgenda() {
        return Agenda.builder()
                     .id( 1L )
                     .name(AGENDA_TITLE)
                     .description(AGENDA_DESCRIPTION)
                     .build();
    }
}
