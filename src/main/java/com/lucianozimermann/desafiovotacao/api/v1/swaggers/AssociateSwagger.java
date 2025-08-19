package com.lucianozimermann.desafiovotacao.api.v1.swaggers;

import com.lucianozimermann.desafiovotacao.api.v1.dto.requests.AssociateDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.AssociateResponseDTO;
import com.lucianozimermann.desafiovotacao.api.v1.dto.responses.CustomErrorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Associados", description = "Operações de gerenciamento de Associados")
public interface AssociateSwagger {

    @Operation(summary = "Cria um novo Associado",
            description = "Registra um associado no sistema.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Associado criado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AssociateResponseDTO.class))),
            @ApiResponse(responseCode = "422", description = "Dados inválidos do Associado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class)))
    })
    ResponseEntity<AssociateResponseDTO> registerAssociate(
            @RequestBody(description = "Dados do Associado", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AssociateDTO.class)))
            AssociateDTO dto
    );

    @Operation(summary = "Busca Associado por ID",
            description = "Retorna os detalhes de um Associado pelo seu ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Associado encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AssociateResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Associado não encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class)))
    })
    ResponseEntity<AssociateResponseDTO> findyById(
            @Parameter(description = "ID do Associado", required = true) Long id
    );

    @Operation(summary = "Lista todos os Associados",
            description = "Retorna uma lista com todos os associados cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de associados",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AssociateResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomErrorDTO.class)))
    })
    ResponseEntity<List<AssociateResponseDTO>> getAllAssociates();
}
