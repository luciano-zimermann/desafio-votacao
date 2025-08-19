package com.lucianozimermann.desafiovotacao.api.v1.repositories;

import com.lucianozimermann.desafiovotacao.api.v1.entities.Associate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class AssociateRepositoryTest {
    private static final String NAME = "Marcos Vinícius";
    private static final String CPF = "22222222222";

    @Autowired
    private AssociateRepository associateRepository;

    @Test
    @DisplayName("Deve salvar e buscar um associado pelo id")
    void saveAndFindById_shouldReturnAssociate() {
        Associate associate = buildAssociate();

        Associate saved = associateRepository.save(associate);

        Optional<Associate> found = associateRepository.findById(saved.getId());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(NAME, found.get().getName());
        Assertions.assertEquals(CPF, found.get().getCpf());
    }

    private Associate buildAssociate() {
        return Associate.builder()
                        .name(NAME)
                        .cpf(CPF)
                        .build();
    }
}
