package com.example.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> userExceptionHandler(UserException ue, WebRequest req){

        ErrorDetail err = new ErrorDetail(ue.getMessage(),req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DriverException.class)
    public ResponseEntity<ErrorDetail> driverExceptionHandler(DriverException de, WebRequest req){

        ErrorDetail err = new ErrorDetail(de.getMessage(),req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(err, HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(RideException.class)
    public ResponseEntity<ErrorDetail> RideExceptionHandler(RideException re, WebRequest req){

        ErrorDetail err = new ErrorDetail(re.getMessage(),req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(err, HttpStatus.ACCEPTED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetail> handleValidationException(ConstraintViolationException ex){
        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()){
            errorMessage.append(violation.getMessage() + "\n");
        }
        ErrorDetail err = new ErrorDetail(errorMessage.toString(),"Validation Error",LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetail> methodArgumentNotValidationExceptionHandler(MethodArgumentNotValidException me){
        ErrorDetail err = new ErrorDetail(me.getBindingResult().getFieldError().getDefaultMessage(),"validation error",LocalDateTime.now());
        return new ResponseEntity<ErrorDetail>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception e, WebRequest req){

        ErrorDetail err = new ErrorDetail(e.getMessage(),req.getDescription(false), LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(err, HttpStatus.ACCEPTED);
    }

}
