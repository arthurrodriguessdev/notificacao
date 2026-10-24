package notificacaotarefas.notificacao.business;

import notificacaotarefas.notificacao.business.dto.TarefaDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoListener {
    @KafkaListener(topics = "tarefa-criada", groupId = "notificacao-group")
    public void processar(TarefaDTO tarefaDTO){
        System.out.println("Processou: " + tarefaDTO.nomeTarefa());
    }
}