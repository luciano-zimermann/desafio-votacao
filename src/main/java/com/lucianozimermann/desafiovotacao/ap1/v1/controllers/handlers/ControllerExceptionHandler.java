package com.lucianozimermann.desafiovotacao.ap1.v1.controllers.handlers;

import com.lucianozimermann.desafiovotacao.ap1.v1.dto.responses.CustomErrorDTO;
import com.lucianozimermann.desafiovotacao.exceptions.EntityNotFoundException;
import com.lucianozimermann.desafiovotacao.exceptions.InvalidAgendaException;
import com.lucianozimermann.desafiovotacao.exceptions.RuleConflictException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomErrorDTO> entityNotFound(EntityNotFoundException e, HttpServletRequest request) {
        return getResponseEntity(e, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidAgendaException.class)
    public ResponseEntity<CustomErrorDTO> invalidAgenda(InvalidAgendaException e, HttpServletRequest request) {
        return getResponseEntity(e, request, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(RuleConflictException.class)
    public ResponseEntity<CustomErrorDTO> sessionAlreadyOpen(RuleConflictException e, HttpServletRequest request) {
        return getResponseEntity(e, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorDTO> handleUnexpected(Exception e, HttpServletRequest request) {
        return getResponseEntity(new RuntimeException("Erro interno no servidor"), request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<CustomErrorDTO> getResponseEntity(RuntimeException e, HttpServletRequest request, HttpStatus status) {
        CustomErrorDTO error = buildCustomError(e, request, status);

        return ResponseEntity.status(status).body(error);
    }

    private CustomErrorDTO buildCustomError(RuntimeException e, HttpServletRequest request, HttpStatus status) {
        CustomErrorDTO error = CustomErrorDTO.builder()
                                             .timestamp(Instant.now())
                                             .status(status.value())
                                             .error(e.getMessage())
                                             .path(request.getRequestURI())
                                             .build();
        return error;
    }
}
