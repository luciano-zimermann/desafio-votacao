package com.lucianozimermann.desafiovotacao.api.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucianozimermann.desafiovotacao.api.v1.dto.requests.AgendaDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.AgendaResponseDTO;
import com.lucianozimermann.desafiovotacao.api.v1.services.AgendaService;
import com.lucianozimermann.desafiovotacao.exceptions.InvalidAgendaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AgendaControllerTest {
    private static final Long AGENDA_ID = 1L;
    private static final String AGENDA_TITLE = "Nova Pauta";
    private static final String AGENDA_DESCRIPTION = "Descrição da Pauta";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve registrar uma pauta corretamente com os dados válidos")
    void createValidAgenda_shouldReturnAgenda() throws Exception {
        AgendaDTO dto = new AgendaDTO(AGENDA_TITLE, AGENDA_DESCRIPTION);
        AgendaResponseDTO responseDTO = new AgendaResponseDTO(AGENDA_ID, AGENDA_TITLE, AGENDA_DESCRIPTION);

        performPostAgendaTest(dto, 200, responseDTO);
    }

    @Test
    @DisplayName("Deve retornar exceção ao registrar pauta com nome inválido")
    void createInvalidAgenda_shouldReturnError() throws Exception {
        AgendaDTO dto = new AgendaDTO(null, AGENDA_DESCRIPTION);

        Mockito.doThrow(new InvalidAgendaException()).when(agendaService).register(Mockito.any(AgendaDTO.class));

        performPostAgendaTest(dto, 422, null);
    }

    @Test
    @DisplayName("Deve retornar a pauta ao buscar por id")
    void findById_shouldReturnAgenda() throws Exception {
        AgendaResponseDTO responseDTO = new AgendaResponseDTO(AGENDA_ID, AGENDA_TITLE, AGENDA_DESCRIPTION);

        Mockito.doReturn(responseDTO).when(agendaService).findById(AGENDA_ID);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/agendas/{id}", AGENDA_ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(AGENDA_ID))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(AGENDA_TITLE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(AGENDA_DESCRIPTION));
    }

    private void performPostAgendaTest(AgendaDTO dto, int expectedStatus, AgendaResponseDTO expectedResponse) throws Exception {
        if (expectedResponse != null) {
            Mockito.doReturn(expectedResponse).when(agendaService).register(Mockito.any(AgendaDTO.class));
        }

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/agendas")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(dto)))
               .andExpect(MockMvcResultMatchers.status().is(expectedStatus));

        if (expectedResponse != null) {
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/agendas")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content(objectMapper.writeValueAsString(dto)))
                   .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(expectedResponse.getId()))
                   .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(expectedResponse.getName()))
                   .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(expectedResponse.getDescription()));
        }
    }

}
