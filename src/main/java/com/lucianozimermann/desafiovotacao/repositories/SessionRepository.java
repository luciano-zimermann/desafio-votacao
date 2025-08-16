package com.lucianozimermann.desafiovotacao.repositories;

import com.lucianozimermann.desafiovotacao.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
