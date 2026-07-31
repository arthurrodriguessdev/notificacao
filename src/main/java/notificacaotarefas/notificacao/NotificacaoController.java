package notificacaotarefas.notificacao;

import lombok.RequiredArgsConstructor;
import notificacaotarefas.notificacao.business.EmailService;
import notificacaotarefas.notificacao.business.dto.TarefaDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/email")
public class NotificacaoController {
    private final EmailService emailService;

    @PostMapping
    public ResponseEntity<Void> enviarEmail(@RequestBody TarefaDTO tarefaDTO){
        emailService.enviarEmail(tarefaDTO);
        return ResponseEntity.ok().build();
    }
}