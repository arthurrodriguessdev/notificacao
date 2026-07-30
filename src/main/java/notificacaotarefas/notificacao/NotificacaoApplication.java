package notificacaotarefas.notificacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NotificacaoApplication {
	public static void main(String[] args) {
		SpringApplication.run(NotificacaoApplication.class, args);
	}
}