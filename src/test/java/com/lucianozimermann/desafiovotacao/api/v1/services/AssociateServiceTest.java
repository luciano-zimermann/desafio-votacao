package com.lucianozimermann.desafiovotacao.api.v1.services;

import com.lucianozimermann.desafiovotacao.api.v1.dto.requests.AssociateDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.AssociateResponseDTO;
import com.lucianozimermann.desafiovotacao.api.v1.entities.Associate;
import com.lucianozimermann.desafiovotacao.exceptions.AssociateNotFoundException;
import com.lucianozimermann.desafiovotacao.api.v1.repositories.AssociateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AssociateServiceTest {
    private static final String ASSOCIATE_NAME = "Mário Silva";
    private static final String ASSOCIATE_CPF = "12345678900";

    @Mock
    private AssociateRepository repository;

    @InjectMocks
    private AssociateService service;

    @Test
    @DisplayName("Deve criar o associado corretamente com os dados válidos")
    void registerAssociate_shouldRegisterAndReturnAssociate() {
        AssociateDTO dto = new AssociateDTO();
        dto.setName(ASSOCIATE_NAME);
        dto.setCpf(ASSOCIATE_CPF);

        Associate savedAssociate = buildAssociate(1L, ASSOCIATE_NAME, ASSOCIATE_CPF);

        Mockito.when(repository.save(Mockito.any(Associate.class))).thenReturn(savedAssociate);

        AssociateResponseDTO responseDTO = service.register(dto);

        Assertions.assertNotNull(responseDTO);
        Assertions.assertEquals(savedAssociate.getId(), responseDTO.getId());
        Assertions.assertEquals(savedAssociate.getName(), responseDTO.getName());
        Assertions.assertEquals(savedAssociate.getCpf(), responseDTO.getCpf());

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(Associate.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando o associado não for encontrado pelo ID")
    void findById_shouldThrowAssociateNotFoundException() {
        Mockito.when(repository.findById(999L)).thenReturn(Optional.empty());

        Assertions.assertThrows(AssociateNotFoundException.class, () -> service.findById(999L));
    }

    @Test
    @DisplayName("Deve retornar uma lista com todos os associados")
    void getAllAssociates_shouldReturnListOfAssociates() {
        List<Associate> associates = Arrays.asList(
                buildAssociate(1L, "João", "11111111111"),
                buildAssociate(2L, "Maria", "22222222222")
        );

        Mockito.when(repository.findAll()).thenReturn(associates);

        List<AssociateResponseDTO> responseList = service.getAllAssociates();

        Assertions.assertEquals(associates.size(), responseList.size());

        for (int i = 0; i < associates.size(); i++) {
            Assertions.assertEquals(associates.get(i).getName(), responseList.get(i).getName());
            Assertions.assertEquals(associates.get(i).getCpf(), responseList.get(i).getCpf());
        }
    }

    private Associate buildAssociate(Long id, String name, String cpf) {
        return Associate.builder()
                        .id(id)
                        .name(name)
                        .cpf(cpf)
                        .build();
    }
}
