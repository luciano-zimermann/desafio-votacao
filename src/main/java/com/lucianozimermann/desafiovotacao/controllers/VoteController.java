package com.lucianozimermann.desafiovotacao.controllers;

import com.lucianozimermann.desafiovotacao.dto.requests.VoteDTO;
import com.lucianozimermann.desafiovotacao.dto.responses.VoteResponseDTO;
import com.lucianozimermann.desafiovotacao.dto.responses.VotingResultResponseDTO;
import com.lucianozimermann.desafiovotacao.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("votes")
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
