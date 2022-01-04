package org.shop.controllers;

import org.shop.exceptions.ClientNotExists;
import org.shop.exceptions.ClientOperationException;
import org.shop.responses.ResponseDTO;
import org.shop.responses.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ClientNotExists.class)
    public ResponseEntity<?> clientNotFoundHandle(Exception exception, WebRequest request) {
        return new ResponseEntity<>(
                new ResponseDTO(new Date(), exception.getMessage(), ResponseStatus.ERROR)
                , HttpStatus.BAD_REQUEST
        );
    }





}
