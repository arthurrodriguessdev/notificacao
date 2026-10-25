package notificacaotarefas.notificacao.infraestructure.exception.handler;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class GlobalErrorMessage {
    private HttpStatus status;
    private String message;
}
