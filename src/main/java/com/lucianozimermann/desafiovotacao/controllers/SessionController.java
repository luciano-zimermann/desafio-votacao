package com.lucianozimermann.desafiovotacao.controllers;

import com.lucianozimermann.desafiovotacao.entities.Session;
import com.lucianozimermann.desafiovotacao.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "sessions")
public class SessionController {

    @Autowired
    private SessionService service;

    @GetMapping(value = "{id}")
    public ResponseEntity<Session> findyById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
