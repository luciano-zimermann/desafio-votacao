package com.lucianozimermann.desafiovotacao.api.v1.controllers;

import com.lucianozimermann.desafiovotacao.api.v1.dto.requests.AgendaDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.AgendaResponseDTO;
import com.lucianozimermann.desafiovotacao.api.v1.services.AgendaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/agendas")
public class AgendaController {
    @Autowired
    private AgendaService service;

    @PostMapping
    public ResponseEntity<AgendaResponseDTO> register(@RequestBody AgendaDTO dto) {
        log.info("POST /agendas - Criando pauta: {}", dto.getName());

       return ResponseEntity.ok(service.register(dto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AgendaResponseDTO> findyById(@PathVariable Long id) {
        log.info("GET /agendas/{} - Buscando pauta", id);

        return ResponseEntity.ok(service.findById(id));
    }
}
