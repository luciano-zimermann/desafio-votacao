package com.lucianozimermann.desafiovotacao.api.v1.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaDTO {
    private String name;
    private String description;
}
