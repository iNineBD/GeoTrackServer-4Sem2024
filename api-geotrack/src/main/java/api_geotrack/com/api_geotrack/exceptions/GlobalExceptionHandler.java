package api_geotrack.com.api_geotrack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
    public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        String message = "Erro de validação: " + ex.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    // Exceção genérica
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        String message = "Ocorreu um erro inesperado. Por favor, tente novamente.";
        String details = ex.getLocalizedMessage();
        return new ResponseEntity<>(message + " Detalhes: " + details, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRunTimeException(RuntimeException ex, WebRequest request) {
        String message = "Ocorreu um erro no código.";
        String details = ex.getLocalizedMessage();
        return new ResponseEntity<>(message + " Detalhes: " + details, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> handleNullPointerException(NullPointerException ex, WebRequest request) {
        String message = "Possivel erro no código.";
        String details = ex.getLocalizedMessage();
        return new ResponseEntity<>(message + " Detalhes: " + details, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String message = "Método chamado com argumento inadequado.";
        String details = ex.getLocalizedMessage();
        return new ResponseEntity<>(message + " Detalhes: " + details, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleIIllegalStateException(IllegalStateException ex, WebRequest request) {
        String message = "Método chamado com argumento inadequado.";
        String details = ex.getLocalizedMessage();
        return new ResponseEntity<>(message + " Detalhes: " + details, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<?> handleIndexOutOfBoundsException(IndexOutOfBoundsException ex, WebRequest request) {
        String message = "Índice inválido.";
        String details = ex.getLocalizedMessage();
        return new ResponseEntity<>(message + " Detalhes: " + details, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        String message = "Solicitação HTTP não corresponde a requisição.";
        String details = ex.getLocalizedMessage();
        return new ResponseEntity<>(message + " Detalhes: " + details, HttpStatus.METHOD_NOT_ALLOWED);
    }
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, WebRequest request) {
        String message = "Tipo de mídia (content type) da solicitação não é suportado";
        String details = ex.getLocalizedMessage();
        return new ResponseEntity<>(message + " Detalhes: " + details, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        String message = "Corpo da solicitação não pode ser convertido no objeto esperado";
        String details = ex.getLocalizedMessage();
        return new ResponseEntity<>(message + " Detalhes: " + details, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request) {
        String message = "URL solicitada não corresponde a nenhum endpoint do servidor.";
        String details = ex.getLocalizedMessage();
        return new ResponseEntity<>(message + " Detalhes: " + details, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        String message = "Você não tem permissão";
        String details = ex.getLocalizedMessage();
        return new ResponseEntity<>(message + " Detalhes: " + details, HttpStatus.BAD_REQUEST);
    }
}
