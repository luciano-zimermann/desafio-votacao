package com.lucianozimermann.desafiovotacao.controllers;

import com.lucianozimermann.desafiovotacao.entities.Associate;
import com.lucianozimermann.desafiovotacao.services.AssociateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "associates")
public class AssociateController {

    @Autowired
    private AssociateService service;

    @GetMapping(value = "{id}")
    public ResponseEntity<Associate> findyById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
