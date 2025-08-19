package com.lucianozimermann.desafiovotacao.ap1.v1.services;

import com.lucianozimermann.desafiovotacao.ap1.v1.dto.requests.AssociateDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.AssociateResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.entities.Associate;
import com.lucianozimermann.desafiovotacao.exceptions.AssociateNotFoundException;
import com.lucianozimermann.desafiovotacao.ap1.v1.repositories.AssociateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AssociateService {
    @Autowired
    private AssociateRepository repository;

    public AssociateResponseDTO register(AssociateDTO dto) {
        log.info("Registrando associado: name='{}', cpf='{}'", dto.getName(), dto.getCpf());

        Associate associate = Associate.builder()
                                       .name(dto.getName())
                                       .cpf(dto.getCpf())
                                       .build();

        associate = repository.save(associate);

        log.info("Associado registrado com sucesso: id={}", associate.getId());

        return buildAssociateResponseDTO(associate);
    }

    public AssociateResponseDTO findById(Long id) {
        log.info("Buscando associado pelo id={}", id);

        Associate associate = repository.findById(id)
                                        .orElseThrow(() -> {
                                            log.warn("Associado não encontrado: id={}", id);
                                            return new AssociateNotFoundException();
                                        });


        log.info("Associado encontrado: id={}, name='{}'", associate.getId(), associate.getName());

        return buildAssociateResponseDTO(associate);
    }

    public List<AssociateResponseDTO> getAllAssociates() {
        log.info("Listando todos os associados");

        List<AssociateResponseDTO> list = repository.findAll()
                                                    .stream()
                                                    .map(a -> buildAssociateResponseDTO(a))
                                                    .collect(Collectors.toList());

        log.info("Total de associados encontrados: {}", list.size());

        return list;
    }

    private AssociateResponseDTO buildAssociateResponseDTO(Associate associate) {
        log.trace("Construindo DTO para associado id={}", associate.getId());

        return AssociateResponseDTO.builder()
                .id(associate.getId())
                .name(associate.getName())
                .cpf(associate.getCpf())
                .build();
    }
}
