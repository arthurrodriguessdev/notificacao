package notificacaotarefas.notificacao.infraestructure.client.security;

import lombok.*;
import notificacaotarefas.notificacao.infraestructure.client.UsuarioClient;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ProvedorToken {
    private String token;
    private UsuarioClient usuarioClient;

    public ProvedorToken(UsuarioClient usuarioClient){
        this.usuarioClient = usuarioClient;
    }

    public Boolean tokenIsValid(){
        if(this.token == null){
            return false;
        }

        return usuarioClient.verificarValidadeToken(this.token).getBody();
    }
}