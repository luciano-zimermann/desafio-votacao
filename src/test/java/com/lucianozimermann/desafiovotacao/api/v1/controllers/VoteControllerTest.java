package com.lucianozimermann.desafiovotacao.api.v1.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucianozimermann.desafiovotacao.api.v1.dto.requests.VoteDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.VoteResponseDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.VotingResultResponseDTO;
import com.lucianozimermann.desafiovotacao.api.v1.services.VoteService;
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

@SpringBootTest
@AutoConfigureMockMvc
public class VoteControllerTest {

    private static final Long VOTE_ID = 1L;
    private static final Long ASSOCIATE_ID = 1L;
    private static final Long SESSION_ID = 1L;
    private static final Long AGENDA_ID = 1L;
    private static final String VOTE_YES = "SIM";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VoteService voteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve registrar voto com sucesso")
    void registerVote_shouldReturnVoteResponse() throws Exception {
        VoteDTO dto = new VoteDTO(ASSOCIATE_ID, SESSION_ID, VOTE_YES);
        VoteResponseDTO responseDTO = buildVoteResponse();

        Mockito.when(voteService.registerVote(Mockito.any(VoteDTO.class))).thenReturn(responseDTO);

        performPost(dto).andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(VOTE_ID))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.associateId").value(ASSOCIATE_ID))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.sessionId").value(SESSION_ID))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.vote").value(VOTE_YES));
    }

    @Test
    @DisplayName("Deve retornar resultado da votação")
    void getVotingResult_shouldReturnResult() throws Exception {
        VotingResultResponseDTO resultDTO = buildVotingResultResponseDTO();

        Mockito.when(voteService.countVotes(AGENDA_ID)).thenReturn(resultDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/votes/result/{agendaId}", AGENDA_ID).accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.agendaId").value(AGENDA_ID))
               .andExpect(MockMvcResultMatchers.jsonPath("$.yes").value(3))
               .andExpect(MockMvcResultMatchers.jsonPath("$.no").value(1))
               .andExpect(MockMvcResultMatchers.jsonPath("$.result").value("Pauta aprovada"));
    }

    private VoteResponseDTO buildVoteResponse() {
        return VoteResponseDTO.builder()
                              .id(VOTE_ID)
                              .associateId(ASSOCIATE_ID)
                              .sessionId(SESSION_ID)
                              .vote(VOTE_YES)
                              .build();
    }

    private VotingResultResponseDTO buildVotingResultResponseDTO() {
        return VotingResultResponseDTO.builder()
                                      .agendaId(AGENDA_ID)
                                      .yes(3L)
                                      .no(1L)
                                      .result("Pauta aprovada")
                                      .build();
    }

    private ResultActions performPost(VoteDTO dto) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
    }
}
