package com.lucianozimermann.desafiovotacao.api.v1.swaggers;

import com.lucianozimermann.desafiovotacao.api.v1.dto.requests.AgendaDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.AgendaResponseDTO;
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

@Tag(name = "Pautas", description = "Operações de gerenciamento de Pautas")
public interface AgendaSwagger {

    @Operation(summary = "Cria uma nova Pauta",
            description = "Registra uma nova Pauta no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pauta criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AgendaResponseDTO.class))),
            @ApiResponse(responseCode = "422", description = "Dados inválidos da Pauta",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class)))
    })
    ResponseEntity<AgendaResponseDTO> register(
            @RequestBody(description = "Dados da Pauta", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AgendaDTO.class)))
            AgendaDTO dto
    );

    @Operation(summary = "Busca Pauta por ID",
            description = "Retorna os detalhes de uma Pauta pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pauta encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AgendaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class)))
    })
    ResponseEntity<AgendaResponseDTO> findyById(
            @Parameter(description = "ID da Pauta", required = true) Long id
    );
}
