package com.lucianozimermann.desafiovotacao.ap1.v1.services;

import com.lucianozimermann.desafiovotacao.ap1.v1.dto.requests.AssociateDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.AssociateResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Associate;
import com.lucianozimermann.desafiovotacao.exceptions.AssociateNotFoundException;
import com.lucianozimermann.desafiovotacao.ap1.v1.repositories.AssociateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssociateService {
    @Autowired
    private AssociateRepository repository;

    public AssociateResponseDTO register(AssociateDTO dto) {
        Associate associate = Associate.builder()
                                       .name(dto.getName())
                                       .cpf(dto.getCpf())
                                       .build();

        associate = repository.save(associate);

        return buildAssociateResponseDTO(associate);
    }

    public AssociateResponseDTO findById(Long id) {
        Associate associate = repository.findById(id)
                                        .orElseThrow(AssociateNotFoundException::new);

        return buildAssociateResponseDTO(associate);
    }

    public List<AssociateResponseDTO> getAllAssociates() {
        return repository.findAll()
                         .stream()
                         .map(a -> buildAssociateResponseDTO(a))
                         .collect(Collectors.toList());
    }

    private AssociateResponseDTO buildAssociateResponseDTO(Associate associate) {
        return AssociateResponseDTO.builder()
                .id(associate.getId())
                .name(associate.getName())
                .cpf(associate.getCpf())
                .build();
    }
}
