package notificacaotarefas.notificacao.business;

import lombok.RequiredArgsConstructor;
import notificacaotarefas.notificacao.infraestructure.client.security.ProvedorToken;
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
    private final ProvedorToken provedorToken;

    private String getTokenAutenticacao(){
        LoginDTO loginDados = LoginDTO.builder()
                .senha(senhaContaServico)
                .email(emailContaServico)
                .build();

        String token = usuarioClient.login(loginDados).getBody();
        provedorToken.setToken(token);
        return token;
    }

    private String formatarToken(String token){
        if(!token.startsWith("Bearer")){
            return "Bearer " + token;
        }

        return token;
    }

    public void enviarEmail(){
        String token = null;

        // Evitando requisição desnecessária para obter token (guarda em memória)
        if(provedorToken.tokenIsValid()){
            token = provedorToken.getToken();
        } else{
            token = getTokenAutenticacao();
        }

        LocalDateTime dataInicio = LocalDateTime.now();
        LocalDateTime dataFim = dataInicio.plusHours(1);
        List<TarefaDTO> tarefasNotificar = tarefaClient.buscarTarefasPorIntervaloDatas(
            dataInicio, dataFim, formatarToken(token)
        );

        if(!tarefasNotificar.isEmpty()){
            System.out.println("nao");
        }
    }
}