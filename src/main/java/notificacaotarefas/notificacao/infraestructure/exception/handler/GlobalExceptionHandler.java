package notificacaotarefas.notificacao.infraestructure.exception.handler;

import notificacaotarefas.notificacao.infraestructure.exception.EmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailException.class)
    public ResponseEntity<GlobalErrorMessage> emailExceptionHandler(EmailException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new GlobalErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
}