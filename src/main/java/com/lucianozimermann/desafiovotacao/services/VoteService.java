package com.lucianozimermann.desafiovotacao.services;

import com.lucianozimermann.desafiovotacao.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    @Autowired
    private VoteRepository repository;
}
