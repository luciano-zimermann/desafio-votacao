package com.lucianozimermann.desafiovotacao.api.v1.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssociateResponseDTO {
    private Long id;
    private String name;
    private String cpf;
}
