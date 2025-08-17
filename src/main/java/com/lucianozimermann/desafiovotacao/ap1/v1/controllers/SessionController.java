package com.lucianozimermann.desafiovotacao.ap1.v1.controllers;

import com.lucianozimermann.desafiovotacao.ap1.v1.dto.requests.SessionDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.SessionResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Session;
import com.lucianozimermann.desafiovotacao.ap1.v1.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/sessions")
public class SessionController {
    @Autowired
    private SessionService service;

    @PostMapping
    public ResponseEntity<SessionResponseDTO> openSession(@RequestBody SessionDTO dto) {
        SessionResponseDTO response = service.openSession(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Session> findyById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
