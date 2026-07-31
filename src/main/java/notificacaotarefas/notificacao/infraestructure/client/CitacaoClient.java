package notificacaotarefas.notificacao.infraestructure.client;

import notificacaotarefas.notificacao.business.dto.CitacaoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "citacao", url = "${spring.client.url.citacao}")
public interface CitacaoClient {
    @GetMapping("/v2/quoteoftheday")
    List<CitacaoDTO> buscarCitacao(@RequestParam("X-Api-Key") String apiKey);
}