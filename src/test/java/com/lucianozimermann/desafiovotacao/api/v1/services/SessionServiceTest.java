package com.lucianozimermann.desafiovotacao.api.v1.services;

import com.lucianozimermann.desafiovotacao.ap1.v1.dto.requests.SessionDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.SessionResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Agenda;
import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Session;
import com.lucianozimermann.desafiovotacao.ap1.v1.enums.SessionStatus;
import com.lucianozimermann.desafiovotacao.ap1.v1.services.SessionService;
import com.lucianozimermann.desafiovotacao.exceptions.SessionAlreadyOpenException;
import com.lucianozimermann.desafiovotacao.ap1.v1.repositories.AgendaRepository;
import com.lucianozimermann.desafiovotacao.ap1.v1.repositories.SessionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {
    private static final Long AGENDA_ID = 1L;
    private static final Integer DURATION = 2;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private SessionService service;

    @Test
    @DisplayName("Deve abrir uma nova sessão quando não existir nenhuma sessão aberta para a pauta")
    void openSession_shouldCreateSessionWhenNoOpensSessionExists() {
        Agenda agendaMock = buildAgenda();

        SessionDTO dto = new SessionDTO(AGENDA_ID, DURATION);

        Mockito.when(agendaRepository.findById(AGENDA_ID)).thenReturn(Optional.of(agendaMock));
        Mockito.when(sessionRepository.findByAgendaIdAndStatus(AGENDA_ID, SessionStatus.OPEN)).thenReturn(null);

        Session savedSession = buildSession(agendaMock);
        Mockito.when(sessionRepository.save(Mockito.any(Session.class))).thenReturn(savedSession);

        SessionResponseDTO response = service.openSession(dto);

        Assertions.assertNotNull(response);

        Assertions.assertEquals(savedSession.getId(), response.getId());
        Assertions.assertEquals(SessionStatus.OPEN.name(), response.getStatus());

        Assertions.assertNotNull(response.getStartDate());
        Assertions.assertNotNull(response.getEndDate());

        Mockito.verify(sessionRepository, Mockito.times(1)).save(Mockito.any(Session.class));
    }

    @Test
    @DisplayName("Não deve abrir uma sessão e lançar uma exceção se já existir uma sessão aberta na pauta")
    void openSession_shouldThrowExceptionWhenOpensSessionsExists() {
        Agenda agendaMock = buildAgenda();
        SessionDTO dto = new SessionDTO(AGENDA_ID, DURATION);

        Mockito.when(agendaRepository.findById(AGENDA_ID)).thenReturn(Optional.of(agendaMock));

        Session existingSession = buildSession(agendaMock);
        existingSession.setEndDate(LocalDateTime.now().plusMinutes(5));
        Mockito.when(sessionRepository.findByAgendaIdAndStatus(AGENDA_ID, SessionStatus.OPEN))
                                      .thenReturn(existingSession);

        Assertions.assertThrows(SessionAlreadyOpenException.class, () -> service.openSession(dto));

        Mockito.verify(sessionRepository, Mockito.never()).save(Mockito.any(Session.class));
    }

    private Agenda buildAgenda() {
        return Agenda.builder()
                     .id(AGENDA_ID)
                     .build();
    }

    private Session buildSession(Agenda agenda) {
        LocalDateTime now = LocalDateTime.now();

        return Session.builder()
                      .id(AGENDA_ID)
                      .agenda(agenda)
                      .startDate(now)
                      .endDate(now.plusMinutes(2))
                      .status(SessionStatus.OPEN)
                      .build();
    }
}
