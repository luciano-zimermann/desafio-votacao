package com.lucianozimermann.desafiovotacao.ap1.v1.controllers;

import com.lucianozimermann.desafiovotacao.ap1.v1.dto.requests.VoteDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.VoteResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.VotingResultResponseDTO;
import com.lucianozimermann.desafiovotacao.ap1.v1.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votes")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @PostMapping
    public ResponseEntity<VoteResponseDTO> registerVote(@RequestBody VoteDTO voteDTO) {
        VoteResponseDTO responseDTO = voteService.registerVote(voteDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/result/{agendaId}")
    public ResponseEntity<VotingResultResponseDTO> getVotingResult(@PathVariable Long agendaId) {
        VotingResultResponseDTO result = voteService.countVotes(agendaId);

        return ResponseEntity.ok(result);
    }
}
