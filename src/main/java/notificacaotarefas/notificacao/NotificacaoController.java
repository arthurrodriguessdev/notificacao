/*
* APENAS PARA TESTES. TO DO: APAGAR ESSA CLASSE APÓS JOB
* */

package notificacaotarefas.notificacao;

import lombok.RequiredArgsConstructor;
import notificacaotarefas.notificacao.business.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {
    private final EmailService emailService;
    @GetMapping
    public ResponseEntity<Void> enviarEmail(){


        emailService.enviarEmail();
        return ResponseEntity.ok().build();
    }
}
