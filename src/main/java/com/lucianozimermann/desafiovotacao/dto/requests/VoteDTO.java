package com.lucianozimermann.desafiovotacao.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoteDTO {
    private Long associateId;
    private Long sessionId;
    private String vote;
}
