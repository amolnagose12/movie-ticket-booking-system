package com.movie.ticket.booking.system.commons.handlers;

import com.movie.ticket.booking.system.commons.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseDTO> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
//        List<ObjectError> errors = methodArgumentNotValidException.getBindingResult().getAllErrors();
//        List<String> errorMessages = new ArrayList<>();
//
//        for (ObjectError error : errors) {
//            errorMessages.add(error.getDefaultMessage());
//        }

        return new ResponseEntity<ResponseDTO>(ResponseDTO.builder()
                .errorDescription(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errorMessages(
                        methodArgumentNotValidException.getBindingResult().getAllErrors()
                                .stream()
                                .map(ObjectError::getDefaultMessage)
                                .collect(Collectors.toList())
                )
                .build(), HttpStatus.BAD_REQUEST);
    }

    /**
     *
     * @param runtimeException
     * @return
     * if payment service is down, to handle the same exception
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO> runtimeException(RuntimeException runtimeException){
        return new ResponseEntity<ResponseDTO>(
                ResponseDTO.builder()
                        .statusCodeDescription(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .errorDescription(runtimeException.getMessage())
                        .build(),HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
