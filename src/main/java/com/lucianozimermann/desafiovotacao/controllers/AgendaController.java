package com.lucianozimermann.desafiovotacao.controllers;

import com.lucianozimermann.desafiovotacao.entities.Agenda;
import com.lucianozimermann.desafiovotacao.services.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "agendas")
public class AgendaController {

    @Autowired
    private AgendaService service;

    @GetMapping(value = "{id}")
    public ResponseEntity<Agenda> findyById(@PathVariable Long id) {
        return ResponseEntity.ok( service.findById(id));
    }
}
