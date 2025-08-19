package com.lucianozimermann.desafiovotacao.ap1.v1.controllers;

import com.lucianozimermann.desafiovotacao.ap1.v1.dto.requests.AssociateDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.AssociateResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.services.AssociateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/associates")
public class AssociateController {
    @Autowired
    private AssociateService service;

    @PostMapping
    public ResponseEntity<AssociateResponseDTO> registerAssociate(@RequestBody AssociateDTO dto) {
        log.info("POST /associates - Criando associado: {}", dto.getName());

        return ResponseEntity.ok(service.register(dto));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AssociateResponseDTO> findyById(@PathVariable Long id) {
        log.info("GET /associates/{} - Buscando associado", id);

        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AssociateResponseDTO>> getAllAssociates() {
        log.info("GET /associates - Buscando todos os associados");

        return ResponseEntity.ok(service.getAllAssociates());
    }
}
