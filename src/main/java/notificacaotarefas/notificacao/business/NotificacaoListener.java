package notificacaotarefas.notificacao.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notificacaotarefas.notificacao.business.dto.TarefaDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificacaoListener {
    private final EmailService emailService;

    @KafkaListener(topics = "tarefa-criada", groupId = "notificacao-group")
    private void notificarTarefaCriada(TarefaDTO tarefaDTO){
        log.warn("A criação da tarefa '{}' será notificada para o e-mail '{}'",
                tarefaDTO.nomeTarefa(),
                tarefaDTO.emailUsuario()
        );
        emailService.notificarTarefaCriada(tarefaDTO);
    }
}