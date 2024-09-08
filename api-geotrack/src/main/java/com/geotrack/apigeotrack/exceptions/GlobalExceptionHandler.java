package com.api_geotrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
    public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestErrorMessage> handleValidationException(MethodArgumentNotValidException ex) {
        RestErrorMessage message = new RestErrorMessage("Erro de validação: " + ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    // Exceção genérica
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestErrorMessage> handleGlobalException(Exception ex) {
        RestErrorMessage message = new RestErrorMessage("Ocorreu um erro inesperado. Por favor, tente novamente."+ ex.getLocalizedMessage());
        return new ResponseEntity<>(message,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<RestErrorMessage> handleRunTimeException(RuntimeException ex) {
        RestErrorMessage message = new RestErrorMessage("Ocorreu um erro no código."+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<RestErrorMessage> handleNullPointerException(NullPointerException ex) {
        RestErrorMessage message = new RestErrorMessage("Possivel erro no código."+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestErrorMessage> handleIllegalArgumentException(IllegalArgumentException ex) {
        RestErrorMessage message = new RestErrorMessage("Método chamado com argumento inadequado."+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIIllegalStateException(IllegalStateException ex) {
        String message = "Método chamado com argumento inadequado.";
        String details = ex.getLocalizedMessage();
        return new ResponseEntity<>(message + " Detalhes: " + details, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<RestErrorMessage> handleIndexOutOfBoundsException(IndexOutOfBoundsException ex) {
        RestErrorMessage message = new RestErrorMessage("Índice inválido."+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RestErrorMessage> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        RestErrorMessage message = new RestErrorMessage("Solicitação HTTP não corresponde a requisição."+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.METHOD_NOT_ALLOWED);
    }
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<RestErrorMessage> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
        RestErrorMessage message = new RestErrorMessage("Tipo de mídia (content type) da solicitação não é suportado"+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        RestErrorMessage message = new RestErrorMessage("Corpo da solicitação não pode ser convertido no objeto esperado"+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<RestErrorMessage> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        RestErrorMessage message = new RestErrorMessage("URL solicitada não corresponde a nenhum endpoint do servidor."+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<RestErrorMessage> handleAccessDeniedException(AccessDeniedException ex) {
        RestErrorMessage message = new RestErrorMessage("Você não tem permissão"+ex.getLocalizedMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
