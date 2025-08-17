package com.lucianozimermann.desafiovotacao.repositories;

import com.lucianozimermann.desafiovotacao.entities.Session;
import com.lucianozimermann.desafiovotacao.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByAgendaIdAndStatus(Long agendaId, SessionStatus sessionStatus);

    List<Session> findAllByAgendaIdAndStatus(Long agendaId, SessionStatus sessionStatus);
}
