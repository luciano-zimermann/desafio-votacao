package com.lucianozimermann.desafiovotacao.api.v1.repositories;

import com.lucianozimermann.desafiovotacao.api.v1.entities.Agenda;
import com.lucianozimermann.desafiovotacao.api.v1.entities.Session;
import com.lucianozimermann.desafiovotacao.api.v1.enums.SessionStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class SessionRepositoryTest {

    private static final Integer DURATION_MINUTES = 10;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Test
    @DisplayName("Deve salvar e buscar uma sessão por agenda e status")
    void saveAndFindByAgendaIdAndStatus_shouldReturnSession() {
        Agenda agenda = buildAgenda();
        agendaRepository.save(agenda);

        Session session = buildSession(agenda);
        sessionRepository.save(session);

        Session foundedSession = sessionRepository.findByAgendaIdAndStatus(agenda.getId(), SessionStatus.OPEN);
        Assertions.assertNotNull(foundedSession);
        Assertions.assertEquals(SessionStatus.OPEN, foundedSession.getStatus());
        Assertions.assertEquals(agenda.getId(), foundedSession.getAgenda().getId());
    }

    @Test
    @DisplayName("Deve buscar todas as sessões por agenda e status")
    void findAllByAgendaIdAndStatus_shouldReturnSessions() {
        Agenda agenda = buildAgenda();
        agendaRepository.save(agenda);

        Session session1 = buildSession(agenda);
        Session session2 = buildSession(agenda);
        session2.setStatus(SessionStatus.CLOSED);

        sessionRepository.save(session1);
        sessionRepository.save(session2);

        List<Session> sessions = sessionRepository.findAllByAgendaIdAndStatus(agenda.getId(), SessionStatus.OPEN);
        Assertions.assertEquals(1, sessions.size());
        Assertions.assertEquals(SessionStatus.OPEN, sessions.get(0).getStatus());
    }

    private Agenda buildAgenda() {
        return Agenda.builder()
                     .name("Nova Pauta")
                     .description("Descrição da Pauta")
                     .build();
    }

    private Session buildSession(Agenda agenda) {
        LocalDateTime start = LocalDateTime.now();
        return Session.builder()
                      .agenda(agenda)
                      .duration(DURATION_MINUTES)
                      .startDate(start)
                      .endDate(start.plusMinutes(DURATION_MINUTES))
                      .status(SessionStatus.OPEN)
                      .build();
    }
}
