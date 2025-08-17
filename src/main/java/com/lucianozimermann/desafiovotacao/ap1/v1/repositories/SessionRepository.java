package com.lucianozimermann.desafiovotacao.ap1.v1.repositories;

import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Session;
import com.lucianozimermann.desafiovotacao.ap1.v1.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByAgendaIdAndStatus(Long agendaId, SessionStatus sessionStatus);

    List<Session> findAllByAgendaIdAndStatus(Long agendaId, SessionStatus sessionStatus);
}
