package com.lucianozimermann.desafiovotacao.repositories;

import com.lucianozimermann.desafiovotacao.entities.Vote;
import com.lucianozimermann.desafiovotacao.enums.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Boolean existsByAssociateIdAndSessionAgendaId(Long associateId, Long agendaId);

    Long countBySessionIdAndVote(Long sessionId, VoteOption vote);
}
