package com.lucianozimermann.desafiovotacao.entities;

import com.lucianozimermann.desafiovotacao.enums.VoteEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "associate_id")
    private Associate associate;

    @ManyToOne
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;

    @Enumerated(EnumType.STRING)
    private VoteEnum vote;
}
