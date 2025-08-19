package com.lucianozimermann.desafiovotacao.api.v1.swaggers;

import com.lucianozimermann.desafiovotacao.api.v1.dto.requests.VoteDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.VoteResponseDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.VotingResultResponseDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.CustomErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Votos", description = "Registro de votos e apuração de resultados")
public interface VoteSwagger {

    @Operation(summary = "Registra um voto",
            description = "Permite que um associado vote em uma sessão de uma Pauta.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Voto registrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VoteResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Sessão ou Associado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class))),
            @ApiResponse(responseCode = "409", description = "Conflito de regra (ex.: voto duplicado ou sessão fechada)",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class))),
            @ApiResponse(responseCode = "422", description = "Dados inválidos do voto",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class)))
    })
    ResponseEntity<VoteResponseDTO> registerVote(
            @RequestBody(description = "Dados do voto (associado, sessão, opção)", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VoteDTO.class)))
            VoteDTO voteDTO
    );

    @Operation(summary = "Resultado da votação de uma Pauta",
            description = "Retorna o resultado da votação de uma Pauta com base nas sessões encerradas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultado da Pauta",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = VotingResultResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class)))
    })
    ResponseEntity<VotingResultResponseDTO> getVotingResult(
            @Parameter(description = "ID da Pauta", required = true) Long agendaId
    );
}
