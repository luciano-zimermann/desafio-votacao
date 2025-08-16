package com.lucianozimermann.desafiovotacao.services;

import com.lucianozimermann.desafiovotacao.entities.Session;
import com.lucianozimermann.desafiovotacao.exceptions.SessionNotFoundException;
import com.lucianozimermann.desafiovotacao.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    @Autowired
    private SessionRepository repository;

    public Session findById( Long id) {
        Session session = repository.findById(id)
                                    .orElseThrow(SessionNotFoundException::new);

        return session;
    }
}
