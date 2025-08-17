package com.lucianozimermann.desafiovotacao.controllers;

import com.lucianozimermann.desafiovotacao.dto.requests.AgendaDTO;
import com.lucianozimermann.desafiovotacao.dto.responses.AgendaResponseDTO;
import com.lucianozimermann.desafiovotacao.entities.Agenda;
import com.lucianozimermann.desafiovotacao.services.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "agendas")
public class AgendaController {
    @Autowired
    private AgendaService service;

    @PostMapping
    public ResponseEntity<AgendaResponseDTO> register(@RequestBody AgendaDTO dto) {
       return ResponseEntity.ok(service.register(dto));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Agenda> findyById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
