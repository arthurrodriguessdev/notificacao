package notificacaotarefas.notificacao.infraestructure.client;

import notificacaotarefas.notificacao.business.dto.TarefaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "tarefa", url = "${tarefa.url}")
public interface TarefaClient {
    @GetMapping("/tarefas/eventos")
    List<TarefaDTO> buscarTarefasPorIntervaloDatas(@RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
                                                   @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
                                                   @RequestHeader("Authorization") String token);
}