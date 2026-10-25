package notificacaotarefas.notificacao.business.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public record TarefaDTO(
        String nomeTarefa,
        String descricaoTarefa,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime dataEvento,
        String emailUsuario
){}