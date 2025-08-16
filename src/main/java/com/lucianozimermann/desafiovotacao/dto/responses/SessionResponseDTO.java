package com.lucianozimermann.desafiovotacao.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionResponseDTO {
    private Long id;
    private Long agendaId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
