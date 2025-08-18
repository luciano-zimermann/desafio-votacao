package com.lucianozimermann.desafiovotacao.client.controllers;

import com.lucianozimermann.desafiovotacao.client.dto.CPFResponseDTO;
import com.lucianozimermann.desafiovotacao.client.enums.CPFVoteStatus;
import com.lucianozimermann.desafiovotacao.client.services.CPFValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client/cpf")
public class CPFValidatorController {

    @Autowired
    private CPFValidatorService cpfValidatorService;

    @GetMapping("/validate/{cpf}")
    public ResponseEntity<CPFResponseDTO> validadeCpf(@PathVariable String cpf) {
        CPFVoteStatus status = cpfValidatorService.validateCPF(cpf);

        return ResponseEntity.ok(CPFResponseDTO.builder()
                                               .status(status.name())
                                               .build());
    }
}
