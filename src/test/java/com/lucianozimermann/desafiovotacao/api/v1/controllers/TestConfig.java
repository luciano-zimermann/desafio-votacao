package com.lucianozimermann.desafiovotacao.api.v1.controllers;

import com.lucianozimermann.desafiovotacao.api.v1.services.AgendaService;
import com.lucianozimermann.desafiovotacao.api.v1.services.AssociateService;
import com.lucianozimermann.desafiovotacao.api.v1.services.SessionService;
import com.lucianozimermann.desafiovotacao.api.v1.services.VoteService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Bean
    public AgendaService agendaService() {
        return Mockito.mock(AgendaService.class);
    }

    @Bean
    public SessionService sessionService() {
        return Mockito.mock(SessionService.class);
    }

    @Bean
    public AssociateService associateService() {
        return Mockito.mock(AssociateService.class);
    }

    @Bean
    public VoteService voteService() {
        return Mockito.mock(VoteService.class);
    }
}