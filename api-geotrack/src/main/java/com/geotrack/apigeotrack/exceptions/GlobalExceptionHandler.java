package com.geotrack.apigeotrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@ControllerAdvice
    public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        RestErrorMessage message = new RestErrorMessage("Erro de validação: " + ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessage> handleGlobalException(Exception ex) {
        ex.printStackTrace();
        RestErrorMessage message = new RestErrorMessage("Ocorreu um erro inesperado. Por favor, tente novamente. "+ ex.getLocalizedMessage());
        return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RestErrorMessage> handleRunTimeException(RuntimeException ex) {
        ex.printStackTrace();
        RestErrorMessage message = new RestErrorMessage("Ocorreu um erro no código. "+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<RestErrorMessage> handleNullPointerException(NullPointerException ex) {
        ex.printStackTrace();
        RestErrorMessage message = new RestErrorMessage("Ocorreu um erro inesperado. "+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestErrorMessage> handleIllegalArgumentException(IllegalArgumentException ex) {
        ex.printStackTrace();
        RestErrorMessage message = new RestErrorMessage(ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIIllegalStateException(IllegalStateException ex) {
        ex.printStackTrace();
        String message = "Método chamado com argumento inadequado.";
        String details = ex.getLocalizedMessage();
        return new ResponseEntity<>(message + " Detalhes: " + details, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<RestErrorMessage> handleIndexOutOfBoundsException(IndexOutOfBoundsException ex) {
        ex.printStackTrace();
        RestErrorMessage message = new RestErrorMessage("Índice inválido. "+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RestErrorMessage> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        ex.printStackTrace();
        RestErrorMessage message = new RestErrorMessage("Solicitação HTTP não corresponde a requisição. "+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<RestErrorMessage> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        ex.printStackTrace();
        RestErrorMessage message = new RestErrorMessage("Tipo de mídia (content type) da solicitação não é suportado "+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        RestErrorMessage message = new RestErrorMessage("Corpo da solicitação não pode ser convertido no objeto esperado "+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<RestErrorMessage> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ex.printStackTrace();
        RestErrorMessage message = new RestErrorMessage("URL solicitada não corresponde a nenhum endpoint do servidor. "+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestErrorMessage> handleAccessDeniedException(AccessDeniedException ex) {
        ex.printStackTrace();
        RestErrorMessage message = new RestErrorMessage("Você não tem permissão " + ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<RestErrorMessage> handleNoResourceFoundException(NoResourceFoundException ex) {
        ex.printStackTrace();
        RestErrorMessage message = new RestErrorMessage("URL solicitada não corresponde a nenhum endpoint do servidor: "+ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<RestErrorMessage> handleNoSuchElementException(NoSuchElementException ex) {
        ex.printStackTrace();
        RestErrorMessage message = new RestErrorMessage(ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<RestErrorMessage> handleNoSuchElementException(IOException ex) {
        ex.printStackTrace();
        RestErrorMessage message = new RestErrorMessage(ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
