package com.lucianozimermann.desafiovotacao.repositories;

import com.lucianozimermann.desafiovotacao.entities.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
}
