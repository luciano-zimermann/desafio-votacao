package com.lucianozimermann.desafiovotacao.ap1.v1.services;

import com.lucianozimermann.desafiovotacao.ap1.v1.dto.requests.SessionDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.SessionResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Agenda;
import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Session;
import com.lucianozimermann.desafiovotacao.ap1.v1.enums.SessionStatus;
import com.lucianozimermann.desafiovotacao.exceptions.AgendaNotFoundException;
import com.lucianozimermann.desafiovotacao.exceptions.SessionAlreadyOpenException;
import com.lucianozimermann.desafiovotacao.exceptions.SessionNotFoundException;
import com.lucianozimermann.desafiovotacao.ap1.v1.repositories.AgendaRepository;
import com.lucianozimermann.desafiovotacao.ap1.v1.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AgendaRepository agendaRepository;

    public SessionResponseDTO openSession(SessionDTO dto){
        Agenda agenda = agendaRepository.findById(dto.getAgendaId())
                                        .orElseThrow(AgendaNotFoundException::new);

        validateExistingOpenSession(dto);

        Session session = Session.builder()
                                 .agenda(agenda)
                                 .startDate(LocalDateTime.now())
                                 .duration(dto.getDuration())
                                 .status(SessionStatus.OPEN)
                                 .build();

        session.calculteSessionEnd();

        session = sessionRepository.save(session);

        return SessionResponseDTO.builder()
                                 .id(session.getId())
                                 .agendaId(agenda.getId())
                                 .startDate(session.getStartDate())
                                 .endDate(session.getEndDate())
                                 .status(session.getStatus().name())
                                 .build();
    }

    public Session findById(Long id) {
        Session session = sessionRepository.findById(id)
                                           .orElseThrow(SessionNotFoundException::new);

        return session;
    }

    private void validateExistingOpenSession(SessionDTO dto) {
        Session existingSession = sessionRepository.findByAgendaIdAndStatus(dto.getAgendaId(), SessionStatus.OPEN);

        if (existingSession != null) {
            if (existingSession.getEndDate().isBefore(LocalDateTime.now())){
                existingSession.setStatus(SessionStatus.CLOSED);
                sessionRepository.save(existingSession);
            } else {
                throw new SessionAlreadyOpenException();
            }
        }
    }
}
