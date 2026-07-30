package notificacaotarefas.notificacao.business;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import notificacaotarefas.notificacao.business.dto.LoginDTO;
import notificacaotarefas.notificacao.business.dto.TarefaDTO;
import notificacaotarefas.notificacao.infraestructure.client.TarefaClient;
import notificacaotarefas.notificacao.infraestructure.client.UsuarioClient;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${email.contaservico}")
    private String emailContaServico;
    @Value("${password.contaservico}")
    private String senhaContaServico;

    private final TarefaClient tarefaClient;
    private final UsuarioClient usuarioClient;

    private String getTokenAutenticacao(){
        LoginDTO loginDados = LoginDTO.builder()
                .senha(senhaContaServico)
                .email(emailContaServico)
                .build();

        return usuarioClient.login(loginDados).getBody();
    }

    private String formatarToken(String token){
        if(!token.startsWith("Bearer")){
            return "Bearer " + token;
        }

        return token;
    }

    public void enviarEmail(){
        LocalDateTime dataInicio = LocalDateTime.now();
        LocalDateTime dataFim = dataInicio.plusHours(1);

        List<TarefaDTO> tarefas = tarefaClient.buscarTarefasPorIntervaloDatas(
            dataInicio, dataFim, formatarToken(getTokenAutenticacao())
        );

        if(!tarefas.isEmpty()){
            // Fazer a lógica de enviar o e-mail
        }
    }
}