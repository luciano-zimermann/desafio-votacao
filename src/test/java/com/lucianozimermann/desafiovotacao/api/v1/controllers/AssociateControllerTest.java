package com.lucianozimermann.desafiovotacao.api.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.requests.AssociateDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.AssociateResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.services.AssociateService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class AssociateControllerTest {

    private static final Long ASSOCIATE_ID_1 = 1L;
    private static final Long ASSOCIATE_ID_2 = 2L;
    private static final String NAME_1 = "João da Silva";
    private static final String NAME_2 = "Maria Souza";
    private static final String CPF_1 = "11111111111";
    private static final String CPF_2 = "22222222222";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AssociateService associateService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve registrar um associado com sucesso")
    void registerAssociate_shouldReturnAssociate() throws Exception {
        AssociateDTO dto = new AssociateDTO(NAME_1, CPF_1);
        AssociateResponseDTO responseDTO = buildResponse(ASSOCIATE_ID_1, NAME_1, CPF_1);

        Mockito.when(associateService.register(Mockito.any(AssociateDTO.class))).thenReturn(responseDTO);

        performPost(dto).andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ASSOCIATE_ID_1))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(NAME_1))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(CPF_1));
    }

    @Test
    @DisplayName("Deve buscar associado por ID")
    void findById_shouldReturnAssociate() throws Exception {
        AssociateResponseDTO responseDTO = buildResponse(ASSOCIATE_ID_1, NAME_1, CPF_1);

        Mockito.when(associateService.findById(ASSOCIATE_ID_1)).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/associates/{id}", ASSOCIATE_ID_1).accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(ASSOCIATE_ID_1))
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(NAME_1))
               .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value(CPF_1));
    }

    @Test
    @DisplayName("Deve listar todos os associados")
    void getAllAssociates_shouldReturnList() throws Exception {
        List<AssociateResponseDTO> list = List.of(
                buildResponse(ASSOCIATE_ID_1, NAME_1, CPF_1),
                buildResponse(ASSOCIATE_ID_2, NAME_2, CPF_2)
        );

        Mockito.when(associateService.getAllAssociates()).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/associates").accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(ASSOCIATE_ID_1))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(NAME_1))
               .andExpect(MockMvcResultMatchers.jsonPath("$[0].cpf").value(CPF_1))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(ASSOCIATE_ID_2))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(NAME_2))
               .andExpect(MockMvcResultMatchers.jsonPath("$[1].cpf").value(CPF_2));
    }

    private AssociateResponseDTO buildResponse(Long id, String name, String cpf) {
        return AssociateResponseDTO.builder()
                                   .id(id)
                                   .name(name)
                                   .cpf(cpf)
                                   .build();
    }

    private ResultActions performPost(AssociateDTO dto) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/associates")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(dto)));
    }
}
