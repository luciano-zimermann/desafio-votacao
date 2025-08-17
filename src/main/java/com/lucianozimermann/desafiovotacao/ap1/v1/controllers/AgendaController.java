package com.lucianozimermann.desafiovotacao.ap1.v1.controllers;

import com.lucianozimermann.desafiovotacao.ap1.v1.dto.requests.AgendaDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.AgendaResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.services.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/agendas")
public class AgendaController {
    @Autowired
    private AgendaService service;

    @PostMapping
    public ResponseEntity<AgendaResponseDTO> register(@RequestBody AgendaDTO dto) {
       return ResponseEntity.ok(service.register(dto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AgendaResponseDTO> findyById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
