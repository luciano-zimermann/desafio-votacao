package com.lucianozimermann.desafiovotacao.services;

import com.lucianozimermann.desafiovotacao.dto.requests.VoteDTO;
import com.lucianozimermann.desafiovotacao.dto.responses.VoteResponseDTO;
import com.lucianozimermann.desafiovotacao.dto.responses.VotingResultResponseDTO;
import com.lucianozimermann.desafiovotacao.entities.Agenda;
import com.lucianozimermann.desafiovotacao.entities.Associate;
import com.lucianozimermann.desafiovotacao.entities.Session;
import com.lucianozimermann.desafiovotacao.entities.Vote;
import com.lucianozimermann.desafiovotacao.enums.SessionStatus;
import com.lucianozimermann.desafiovotacao.enums.VoteOption;
import com.lucianozimermann.desafiovotacao.exceptions.*;
import com.lucianozimermann.desafiovotacao.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {
    @Mock
    private VoteRepository voteRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private AssociateRepository associateRepository;

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private VoteService voteService;

    @InjectMocks
    private SessionService sessionService;

    @InjectMocks
    private AssociateService associateService;

    @InjectMocks
    private AgendaService agendaService;

    private static final Long ASSOCIATE_ID = 1L;
    private static final Long SESSION_ID = 1L;
    private static final Long AGENDA_ID = 1L;

    @Test
    @DisplayName("Deve registrar um voto corretamente")
    void registerVote_shouldSaveAndReturnVote() {
        Session session = buildSession(SessionStatus.OPEN, LocalDateTime.now().plusMinutes(10));
        Associate associate = buildAssociate(ASSOCIATE_ID, "João", "11111111111");
        VoteDTO dto = new VoteDTO(ASSOCIATE_ID, SESSION_ID, "YES");
        Vote savedVote = buildVote(associate, session, VoteOption.YES);

        Mockito.when(sessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(session));
        Mockito.when(associateRepository.findById(ASSOCIATE_ID)).thenReturn(Optional.of(associate));
        Mockito.when(voteRepository.existsByAssociateIdAndSessionAgendaId(ASSOCIATE_ID, AGENDA_ID)).thenReturn(false);
        Mockito.when(voteRepository.save(Mockito.any(Vote.class))).thenReturn(savedVote);

        VoteResponseDTO response = voteService.registerVote(dto);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(savedVote.getVote(), VoteOption.valueOf(response.getVote()));
        Mockito.verify(voteRepository, Mockito.times(1)).save(Mockito.any(Vote.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando sessão estiver fechada")
    void registerVote_shouldThrowExceptionWhenSessionIsClosed() {
        Session session = buildSession(SessionStatus.CLOSED, LocalDateTime.now().minusMinutes(10));
        VoteDTO dto = new VoteDTO(ASSOCIATE_ID, SESSION_ID, "YES");

        Mockito.when(sessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(session));

        Assertions.assertThrows(SessionClosedException.class, () -> voteService.registerVote(dto));
    }

    @Test
    @DisplayName("Deve lançar exceção se associado já votou")
    void registerVote_shouldThrowIfAssociateAlreadyVoted() {
        Session session = buildSession(SessionStatus.OPEN, LocalDateTime.now().plusMinutes(10));

        VoteDTO voteDTO = new VoteDTO(ASSOCIATE_ID, SESSION_ID, "YES");

        Mockito.when(sessionRepository.findById(SESSION_ID)).thenReturn(Optional.of(session));
        Mockito.when(voteRepository.existsByAssociateIdAndSessionAgendaId(ASSOCIATE_ID, session.getAgenda().getId())).thenReturn(true);

        Assertions.assertThrows(AssociateAlreadyVotedInAgendaException.class, () -> voteService.registerVote(voteDTO));

        Mockito.verify(voteRepository, Mockito.never()).save(Mockito.any(Vote.class));
    }

    @Test
    @DisplayName("Deve retornar Pauta aprovada quando votos de YES forem maiores do que NO")
    void countVotes_shouldReturnApprovedResult() {
        assertVotingResult(3L, 1L, "Pauta aprovada");
    }

    @Test
    @DisplayName("Deve retornar Pauta rejeitada quando votos de NO forem maiores ou igual do que YES")
    void countVotes_shouldReturnRejectedResult() {
        assertVotingResult(1L, 2L, "Pauta rejeitada");
    }

    private void assertVotingResult(Long yesVotes, Long noVotes, String expectedResult) {
        Agenda agenda = buildAgenda();
        Session closedSession = buildSession(SessionStatus.CLOSED, LocalDateTime.now().minusMinutes(5));

        Mockito.when(agendaRepository.findById(AGENDA_ID)).thenReturn(Optional.of(agenda));
        Mockito.when(sessionRepository.findAllByAgendaIdAndStatus(AGENDA_ID, SessionStatus.CLOSED)).thenReturn(List.of(closedSession));
        Mockito.when(voteRepository.countBySessionIdAndVote(closedSession.getId(), VoteOption.YES)).thenReturn(yesVotes);
        Mockito.when(voteRepository.countBySessionIdAndVote(closedSession.getId(), VoteOption.NO)).thenReturn(noVotes);

        VotingResultResponseDTO result = voteService.countVotes(AGENDA_ID);

        Assertions.assertEquals(AGENDA_ID, result.getAgendaId());
        Assertions.assertEquals(yesVotes, result.getYes());
        Assertions.assertEquals(noVotes, result.getNo());
        Assertions.assertEquals(expectedResult, result.getResult());
    }

    private Session buildSession(SessionStatus status, LocalDateTime endDate) {
        return Session.builder()
                      .id(SESSION_ID)
                      .agenda(buildAgenda())
                      .status(status)
                      .endDate(endDate)
                      .build();
    }

    private Associate buildAssociate(Long id, String name, String cpf) {
        return Associate.builder()
                        .id(id)
                        .name(name)
                        .cpf(cpf)
                        .build();
    }

    private Vote buildVote(Associate associate, Session session, VoteOption option) {
        return Vote.builder()
                   .id(1L)
                   .associate(associate)
                   .session(session)
                   .vote(option)
                   .build();
    }

    private Agenda buildAgenda() {
        return Agenda.builder()
                     .id(AGENDA_ID)
                     .name("Nova Pauta")
                     .description("Descrição da Pauta")
                     .build();
    }
}
