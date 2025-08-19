package com.lucianozimermann.desafiovotacao.api.v1.controllers;

import com.lucianozimermann.desafiovotacao.api.v1.dto.requests.SessionDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.SessionResponseDTO;
import com.lucianozimermann.desafiovotacao.api.v1.services.SessionService;
import com.lucianozimermann.desafiovotacao.api.v1.swaggers.SessionSwagger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/sessions")
public class SessionController implements SessionSwagger {
    @Autowired
    private SessionService service;

    @PostMapping
    public ResponseEntity<SessionResponseDTO> openSession(@RequestBody SessionDTO dto) {
        log.info("POST /sessions - Abrindo sessão para agendaId={} com duração={}", dto.getAgendaId(), dto.getDuration());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.openSession(dto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SessionResponseDTO> findyById(@PathVariable Long id) {
        log.info("GET /sessions/{} - Buscando sessão", id);

        return ResponseEntity.ok(service.findById(id));
    }
}
