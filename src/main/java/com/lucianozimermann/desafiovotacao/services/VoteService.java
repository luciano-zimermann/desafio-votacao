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
import com.lucianozimermann.desafiovotacao.exceptions.AgendaNotFoundException;
import com.lucianozimermann.desafiovotacao.exceptions.AgendaWithoutClosedSessionsException;
import com.lucianozimermann.desafiovotacao.exceptions.AssociateNotFoundException;
import com.lucianozimermann.desafiovotacao.exceptions.SessionNotFoundException;
import com.lucianozimermann.desafiovotacao.repositories.AgendaRepository;
import com.lucianozimermann.desafiovotacao.repositories.AssociateRepository;
import com.lucianozimermann.desafiovotacao.repositories.SessionRepository;
import com.lucianozimermann.desafiovotacao.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AssociateRepository associateRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    public VoteResponseDTO registerVote(VoteDTO dto) {
        Session session = getSession(dto);

        if (LocalDateTime.now().isAfter(session.getEndDate())) {
            throw new RuntimeException("A sessão de votação está encerrada!");
        }

        if (voteRepository.existsByAssociateIdAndSessionAgendaId(dto.getAssociateId(), session.getAgenda().getId())) {
            throw new RuntimeException("Este associado já votou nessa Pauta!");
        }

        Associate associate = associateRepository.findById(dto.getAssociateId())
                                                 .orElseThrow(AssociateNotFoundException::new);

        Vote vote = buildVote(dto, associate, session);

        Vote savedVote = voteRepository.save(vote);

        return buildVoteResponseDTO(savedVote);
    }

    public VotingResultResponseDTO countVotes(Long agendaId) {
        Agenda agenda = agendaRepository.findById(agendaId)
                                        .orElseThrow(AgendaNotFoundException::new);

        List<Session> closedSessions = sessionRepository.findAllByAgendaIdAndStatus(agenda.getId(), SessionStatus.CLOSED);

        if (closedSessions.isEmpty()) {
            Session openSession = sessionRepository.findByAgendaIdAndStatus(agendaId, SessionStatus.OPEN);

            if (openSession != null && openSession.getEndDate().isBefore(LocalDateTime.now())) {
                openSession.setStatus(SessionStatus.CLOSED);

                Session updatedSession = sessionRepository.save(openSession);

                closedSessions.add(updatedSession);
            } else {
                throw new AgendaWithoutClosedSessionsException();
            }
        }

        Long yes = 0L;
        Long no = 0L;

        for (Session session : closedSessions) {
            yes += voteRepository.countBySessionIdAndVote(session.getId(), VoteOption.YES);
            no += voteRepository.countBySessionIdAndVote(session.getId(), VoteOption.NO);
        }

        String result = yes > no ? "Pauta aprovada" : "Pauta rejeitada";

        return VotingResultResponseDTO.builder()
                                      .agendaId(agenda.getId())
                                      .yes(yes)
                                      .no(no)
                                      .result(result)
                                      .build();
    }

    private Session getSession(VoteDTO dto) {
        return sessionRepository.findById(dto.getSessionId())
                                .orElseThrow(SessionNotFoundException::new);
    }

    private Vote buildVote(VoteDTO dto, Associate associate, Session session) {
        return Vote.builder()
                   .associate(associate)
                   .session(session)
                   .vote(VoteOption.valueOf(dto.getVote().toUpperCase()))
                   .build();
    }

    private VoteResponseDTO buildVoteResponseDTO(Vote vote) {
        return VoteResponseDTO.builder()
                              .id(vote.getId())
                              .associateId(vote.getAssociate().getId())
                              .sessionId(vote.getSession().getId())
                              .vote(vote.getVote().name())
                              .build();
    }
}
