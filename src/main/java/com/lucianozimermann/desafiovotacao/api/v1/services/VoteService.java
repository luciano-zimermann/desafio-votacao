package com.lucianozimermann.desafiovotacao.api.v1.services;

import com.lucianozimermann.desafiovotacao.api.v1.dto.requests.VoteDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.VoteResponseDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.VotingResultResponseDTO;
import com.lucianozimermann.desafiovotacao.api.v1.entities.Agenda;
import com.lucianozimermann.desafiovotacao.api.v1.entities.Associate;
import com.lucianozimermann.desafiovotacao.api.v1.entities.Session;
import com.lucianozimermann.desafiovotacao.api.v1.entities.Vote;
import com.lucianozimermann.desafiovotacao.api.v1.enums.SessionStatus;
import com.lucianozimermann.desafiovotacao.api.v1.enums.VoteOption;
import com.lucianozimermann.desafiovotacao.exceptions.*;
import com.lucianozimermann.desafiovotacao.api.v1.repositories.AgendaRepository;
import com.lucianozimermann.desafiovotacao.api.v1.repositories.AssociateRepository;
import com.lucianozimermann.desafiovotacao.api.v1.repositories.SessionRepository;
import com.lucianozimermann.desafiovotacao.api.v1.repositories.VoteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
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
        log.info("Registrando voto: associateId={}, sessionId={}, vote={}", dto.getAssociateId(), dto.getSessionId(), dto.getVote());

        Session session = getSession(dto);

        if (session.getStatus() == SessionStatus.OPEN && session.getEndDate().isBefore(LocalDateTime.now())) {
            session.setStatus(SessionStatus.CLOSED);
            sessionRepository.save(session);

            log.info("Sessão fechada automaticamente por expiração: sessionId={}", session.getId());
        }

        if (session.getStatus() == SessionStatus.CLOSED) {
            log.warn("Tentativa de votar em sessão fechada: sessionId={}", session.getId());

            throw new SessionClosedException();
        }

        if (voteRepository.existsByAssociateIdAndSessionAgendaId(dto.getAssociateId(), session.getAgenda().getId())) {
            log.warn("Associado já votou nesta pauta: associateId={}, agendaId={}", dto.getAssociateId(), session.getAgenda().getId());

            throw new AssociateAlreadyVotedInAgendaException();
        }

        Associate associate = associateRepository.findById(dto.getAssociateId())
                                                 .orElseThrow(() -> {
                                                     log.warn("Associado não encontrado: id={}", dto.getAssociateId());
                                                     return new AssociateNotFoundException();
                                                 });

        Vote vote = buildVote(dto, associate, session);

        Vote savedVote = voteRepository.save(vote);

        log.info("Voto registrado com sucesso: voteId={}, associateId={}, sessionId={}", savedVote.getId(), associate.getId(), session.getId());

        return buildVoteResponseDTO(savedVote);
    }

    public VotingResultResponseDTO countVotes(Long agendaId) {
        log.info("Contando votos para a Pauta: agendaId={}", agendaId);

        Agenda agenda = agendaRepository.findById(agendaId)
                                        .orElseThrow(() -> {
                                            log.warn("Pauta não encontrada: id={}", agendaId);
                                            return new AgendaNotFoundException();
                                        });

        List<Session> closedSessions = sessionRepository.findAllByAgendaIdAndStatus(agenda.getId(), SessionStatus.CLOSED);

        if (closedSessions.isEmpty()) {
            Session openSession = sessionRepository.findByAgendaIdAndStatus(agendaId, SessionStatus.OPEN);

            if (openSession != null && openSession.getEndDate().isBefore(LocalDateTime.now())) {
                openSession.setStatus(SessionStatus.CLOSED);

                Session updatedSession = sessionRepository.save(openSession);

                closedSessions.add(updatedSession);

                log.info("Sessão aberta fechada automaticamente durante contagem: sessionId={}", updatedSession.getId());
            } else {
                log.warn("Nenhuma sessão fechada encontrada para agendaId={}", agendaId);

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

        log.info("Resultado final da votação para agendaId={} -> YES={}, NO={}, RESULT={}", agendaId, yes, no, result);

        return buildVotingResultResponseDTO( agenda, yes, no, result );
    }

    private Session getSession(VoteDTO dto) {
        log.info("Buscando sessão: sessionId={}", dto.getSessionId());

        return sessionRepository.findById(dto.getSessionId())
                                .orElseThrow(() -> {
                                    log.warn("Sessão não encontrada: id={}", dto.getSessionId());
                                    return new SessionNotFoundException();
                                });
    }

    private Vote buildVote(VoteDTO dto, Associate associate, Session session) {
        log.trace("Construindo entidade Vote: associateId={}, sessionId={}, vote={}", associate.getId(), session.getId(), dto.getVote());

        return Vote.builder()
                   .associate(associate)
                   .session(session)
                   .vote(VoteOption.valueOf(dto.getVote().toUpperCase()))
                   .build();
    }

    private VoteResponseDTO buildVoteResponseDTO(Vote vote) {
        log.trace("Construindo DTO para voto id={}", vote.getId());

        return VoteResponseDTO.builder()
                              .id(vote.getId())
                              .associateId(vote.getAssociate().getId())
                              .sessionId(vote.getSession().getId())
                              .vote(vote.getVote().name())
                              .build();
    }

    private VotingResultResponseDTO buildVotingResultResponseDTO( Agenda agenda, Long yes, Long no, String result )
    {
        log.trace("Construindo DTO para o Resultado da Votação: agendaId={}, yes={}, no={}, result={}", agenda.getId(), yes, no, result);
        return VotingResultResponseDTO.builder()
                                      .agendaId( agenda.getId() )
                                      .yes( yes )
                                      .no( no )
                                      .result( result )
                                      .build();
    }
}
