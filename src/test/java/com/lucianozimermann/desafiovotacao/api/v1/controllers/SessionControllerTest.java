package com.lucianozimermann.desafiovotacao.api.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.requests.SessionDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.SessionResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.services.SessionService;
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

import java.time.LocalDateTime;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionControllerTest {

    private static final Long SESSION_ID = 1L;
    private static final Long AGENDA_ID = 1L;
    private static final int DURATION_MINUTES = 10;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve abrir sessão com sucesso")
    void openSession_shouldReturnSession() throws Exception {
        SessionDTO dto = new SessionDTO(AGENDA_ID, DURATION_MINUTES);
        SessionResponseDTO responseDTO = buildSessionResponse();

        Mockito.when(sessionService.openSession(Mockito.any(SessionDTO.class))).thenReturn(responseDTO);

        performPost(dto).andExpect(MockMvcResultMatchers.status().isCreated())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(SESSION_ID))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.agendaId").value(AGENDA_ID))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OPEN"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").isNotEmpty())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").isNotEmpty());
    }

    @Test
    @DisplayName("Deve buscar sessão por ID")
    void findById_shouldReturnSession() throws Exception {
        SessionResponseDTO responseDTO = buildSessionResponse();

        Mockito.when(sessionService.findById(SESSION_ID)).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sessions/{id}", SESSION_ID).accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(SESSION_ID))
               .andExpect(MockMvcResultMatchers.jsonPath("$.agendaId").value(AGENDA_ID))
               .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OPEN"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").isNotEmpty())
               .andExpect(MockMvcResultMatchers.jsonPath("$.endDate").isNotEmpty());
    }

    private SessionResponseDTO buildSessionResponse() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(DURATION_MINUTES);

        return SessionResponseDTO.builder()
                                 .id(SESSION_ID)
                                 .agendaId(AGENDA_ID)
                                 .startDate(start)
                                 .endDate(end)
                                 .status("OPEN")
                                 .build();
    }

    private ResultActions performPost(SessionDTO dto) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/sessions")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(objectMapper.writeValueAsString(dto)));
    }
}
