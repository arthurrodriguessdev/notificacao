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

    @KafkaListener(
            topics = "${app.kafka.topic.task-created}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    private void notificarTarefaCriada(TarefaDTO tarefaDTO){
        log.info("Evento de criação de tarefa recebido");
        emailService.notificarTarefaCriada(tarefaDTO);
    }
}