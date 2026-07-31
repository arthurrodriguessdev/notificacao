package notificacaotarefas.notificacao.business.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefaDTO {
    private Long id;
    private String nomeTarefa;
    private String descricaoTarefa;
    private LocalDateTime dataEvento;
    private String emailUsuario;
}