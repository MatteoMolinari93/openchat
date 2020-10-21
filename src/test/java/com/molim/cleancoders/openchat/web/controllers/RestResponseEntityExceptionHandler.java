package com.molim.cleancoders.openchat.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.molim.cleancoders.openchat.web.exceptions.UsernameAlreadyInUseExcpetion;
import com.molim.cleancoders.openchat.web.models.ErrorDto;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

@ExceptionHandler(value = { UsernameAlreadyInUseExcpetion.class })
protected ResponseEntity<ErrorDto> handleConflict(RuntimeException ex, WebRequest request) {
    return new ResponseEntity<ErrorDto>(new ErrorDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
}
}
