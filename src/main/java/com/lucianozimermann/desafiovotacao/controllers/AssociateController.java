package com.lucianozimermann.desafiovotacao.controllers;

import com.lucianozimermann.desafiovotacao.dto.requests.AssociateDTO;
import com.lucianozimermann.desafiovotacao.dto.responses.AssociateResponseDTO;
import com.lucianozimermann.desafiovotacao.services.AssociateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "associates")
public class AssociateController {
    @Autowired
    private AssociateService service;

    @PostMapping
    public ResponseEntity<AssociateResponseDTO> registerAssociate(@RequestBody AssociateDTO dto) {
        return ResponseEntity.ok(service.register(dto));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<AssociateResponseDTO> findyById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<AssociateResponseDTO>> getAllAssociates() {
        List<AssociateResponseDTO> associates = service.getAllAssociates();

        return ResponseEntity.ok(associates);
    }
}
