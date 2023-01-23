package com.game.exeption_handing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class PlayerGlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handleException(NoSuchPlayerException exception){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(BadException exception){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
