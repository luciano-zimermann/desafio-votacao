package com.lucianozimermann.desafiovotacao.ap1.v1.controllers;

import com.lucianozimermann.desafiovotacao.ap1.v1.dto.requests.VoteDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.VoteResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.VotingResultResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.services.VoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteResponseDTO> registerVote(@RequestBody VoteDTO voteDTO) {
        log.info("POST /votes - associadoId={} votando na sessãoId={} com opção={}", voteDTO.getAssociateId(), voteDTO.getSessionId(), voteDTO.getVote());

        return ResponseEntity.ok(voteService.registerVote(voteDTO));
    }

    @GetMapping("/result/{agendaId}")
    public ResponseEntity<VotingResultResponseDTO> getVotingResult(@PathVariable Long agendaId) {
        log.info("GET /votes/result/{} - Contabilizando votos para a pauta", agendaId);

        return ResponseEntity.ok(voteService.countVotes(agendaId));
    }
}
