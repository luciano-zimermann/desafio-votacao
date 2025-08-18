package com.lucianozimermann.desafiovotacao.api.v1.repositories;

import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Agenda;
import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Associate;
import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Session;
import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Vote;
import com.lucianozimermann.desafiovotacao.ap1.v1.enums.SessionStatus;
import com.lucianozimermann.desafiovotacao.ap1.v1.enums.VoteOption;
import com.lucianozimermann.desafiovotacao.ap1.v1.repositories.AgendaRepository;
import com.lucianozimermann.desafiovotacao.ap1.v1.repositories.AssociateRepository;
import com.lucianozimermann.desafiovotacao.ap1.v1.repositories.SessionRepository;
import com.lucianozimermann.desafiovotacao.ap1.v1.repositories.VoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    @Test
    @DisplayName("Deve salvar voto somente em sessão aberta")
    void saveVote_onlyWhenSessionOpen() {
        Associate associate = buildAssociate();
        associateRepository.save(associate);

        Session openSession = buildSession(SessionStatus.OPEN);
        sessionRepository.save(openSession);

        Vote vote = buildVote(associate, openSession, VoteOption.YES);
        voteRepository.save(vote);

        Boolean exists = voteRepository.existsByAssociateIdAndSessionAgendaId(associate.getId(), openSession.getAgenda().getId());
        Assertions.assertTrue(exists);
    }

    @Test
    @DisplayName("Deve contar votos de sessões fechadas de uma agenda")
    void countVotes_onlyClosedSessions() {
        Associate associate1 = buildAssociate();
        Associate associate2 = buildAssociate();
        associateRepository.save(associate1);
        associateRepository.save(associate2);

        Session closedSession1 = buildSession(SessionStatus.CLOSED);
        Session closedSession2 = buildSession(SessionStatus.CLOSED);
        sessionRepository.save(closedSession1);
        sessionRepository.save(closedSession2);

        voteRepository.save(buildVote(associate1, closedSession1, VoteOption.YES));
        voteRepository.save(buildVote(associate2, closedSession1, VoteOption.NO));
        voteRepository.save(buildVote(associate1, closedSession2, VoteOption.YES));
        voteRepository.save(buildVote(associate2, closedSession2, VoteOption.YES));

        Long yesCountSession1 = voteRepository.countBySessionIdAndVote(closedSession1.getId(), VoteOption.YES);
        Long noCountSession1 = voteRepository.countBySessionIdAndVote(closedSession1.getId(), VoteOption.NO);

        Long yesCountSession2 = voteRepository.countBySessionIdAndVote(closedSession2.getId(), VoteOption.YES);

        Assertions.assertEquals(1L, yesCountSession1);
        Assertions.assertEquals(1L, noCountSession1);
        Assertions.assertEquals(2L, yesCountSession2);
    }

    private static Associate buildAssociate() {
        return Associate.builder()
                        .name("João Kleber")
                        .cpf("04204204204")
                        .build();
    }

    private Session buildSession(SessionStatus status) {
        Agenda agenda = Agenda.builder()
                              .name("Nova Pauta")
                              .description("Descrição da Pauta")
                              .build();

        agendaRepository.save(agenda);

        return Session.builder()
                      .agenda(agenda)
                      .duration(10)
                      .status(status)
                      .build();
    }

    private static Vote buildVote(Associate associate, Session session, VoteOption option) {
        return Vote.builder()
                   .associate(associate)
                   .session(session)
                   .vote(option)
                   .build();
    }
}
