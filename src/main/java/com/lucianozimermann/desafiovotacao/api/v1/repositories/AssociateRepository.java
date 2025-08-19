package com.lucianozimermann.desafiovotacao.api.v1.repositories;

import com.lucianozimermann.desafiovotacao.api.v1.entities.Associate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociateRepository extends JpaRepository<Associate, Long> {
}
