package org.shop.controllers;

import org.shop.exceptions.ClientNotExists;

import org.shop.responses.ResponseDTO;
import org.shop.responses.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ClientNotExists.class)
    public ResponseEntity<?> clientNotFoundHandle(Exception exception) {
        return new ResponseEntity<>(
                new ResponseDTO(new Date(), exception.getMessage(), ResponseStatus.ERROR)
                , HttpStatus.BAD_REQUEST
        );
    }





}
