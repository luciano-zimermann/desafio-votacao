package com.lucianozimermann.desafiovotacao.ap1.v1.repositories;

import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Vote;
import com.lucianozimermann.desafiovotacao.ap1.v1.enums.VoteOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Boolean existsByAssociateIdAndSessionAgendaId(Long associateId, Long agendaId);

    Long countBySessionIdAndVote(Long sessionId, VoteOption vote);
}
