package com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaResponseDTO {
    private Long id;
    private String name;
    private String description;
}
