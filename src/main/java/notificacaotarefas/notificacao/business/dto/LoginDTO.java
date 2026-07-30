package notificacaotarefas.notificacao.business.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    private String email;
    private String senha;
}