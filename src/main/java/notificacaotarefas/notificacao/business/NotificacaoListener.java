package notificacaotarefas.notificacao.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notificacaotarefas.notificacao.business.dto.TarefaDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Listener responsável por consumir eventos relacionados às tarefas
 * e acionar o envio das notificações por e-mail.
 *
 * <p>Consome eventos dos tópicos Kafka (produzido pelo serviço de agendador de tarefas)
 * configurados para identificar a criação de novas tarefas e a proximidade de
 * tarefas agendadas.</p>
 */
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

    @KafkaListener(
        topics = "${app.kafka.topic.next-task}",
        groupId = "${spring.kafka.consumer.group-id}"
    )
    private void notificarTarefaProxima(TarefaDTO tarefaDTO){
        log.info("Evento de notificação de próxima tarefa recebido");
        emailService.notificarProximaTarefa(tarefaDTO);
    }
}