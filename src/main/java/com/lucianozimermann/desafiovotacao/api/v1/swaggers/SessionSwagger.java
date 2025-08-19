package com.lucianozimermann.desafiovotacao.api.v1.swaggers;
import com.lucianozimermann.desafiovotacao.api.v1.dto.requests.SessionDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.SessionResponseDTO;
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

@Tag(name = "Sessões", description = "Abertura e consulta de sessões de votação")
public interface SessionSwagger {

    @Operation(summary = "Abre uma sessão para Pauta",
            description = "Abre uma nova sessão de votação para uma Pauta existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sessão aberta",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SessionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class))),
            @ApiResponse(responseCode = "409", description = "Já existe sessão aberta para esta Pauta",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class))),
            @ApiResponse(responseCode = "422", description = "Dados inválidos para abertura da sessão",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class)))
    })
    ResponseEntity<SessionResponseDTO> openSession(
            @RequestBody(description = "Dados da sessão (ex.: agendaId, duração)", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SessionDTO.class)))
            SessionDTO dto
    );

    @Operation(summary = "Busca Sessão por ID",
            description = "Retorna os detalhes de uma sessão pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sessão encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SessionResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class)))
    })
    ResponseEntity<SessionResponseDTO> findyById(
            @Parameter(description = "ID da Sessão", required = true) Long id
    );
}
