package com.lucianozimermann.desafiovotacao.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotingResultResponseDTO {
    private Long agendaId;
    private Long yes;
    private Long no;
    private String result;
}
