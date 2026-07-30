package notificacaotarefas.notificacao.infraestructure.client;

import notificacaotarefas.notificacao.business.dto.LoginDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {
    @PostMapping("/usuarios/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO);
}
