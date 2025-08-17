package com.lucianozimermann.desafiovotacao.ap1.v1.repositories;

import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
}
