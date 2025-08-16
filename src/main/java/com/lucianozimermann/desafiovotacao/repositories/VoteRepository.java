package com.lucianozimermann.desafiovotacao.repositories;

import com.lucianozimermann.desafiovotacao.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
