package notificacaotarefas.notificacao.infraestructure.handler;

import notificacaotarefas.notificacao.infraestructure.exception.EmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailException.class)
    private ResponseEntity<GlobalErrorMessage> EmailExceptionHandler(EmailException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GlobalErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }
}