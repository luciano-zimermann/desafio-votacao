package com.lucianozimermann.desafiovotacao.controllers;

import com.lucianozimermann.desafiovotacao.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class VoteController {
    @Autowired
    private VoteService service;
}
