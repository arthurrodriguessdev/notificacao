package notificacaotarefas.notificacao.business;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import notificacaotarefas.notificacao.business.dto.CitacaoDTO;
import notificacaotarefas.notificacao.business.dto.TarefaDTO;
import notificacaotarefas.notificacao.infraestructure.client.CitacaoClient;
import notificacaotarefas.notificacao.infraestructure.exception.EmailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.mail.username}")
    private String remetente;
    @Value("${spring.mail.nomeRemetente}")
    private String nomeRemetente;
    @Value("${spring.client.key.citacao}")
    private String keyApiCitacao;
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final CitacaoClient citacaoClient;

    /**
     * Envia um e-mail notificando o usuário sobre sua próxima tarefa.
     *
     * @param tarefaDTO dados da tarefa e informações do usuário destinatário
     */
    public void notificarProximaTarefa(TarefaDTO tarefaDTO){
        Context context = getContextTarefa(tarefaDTO);
        sendEmail(
                tarefaDTO.emailUsuario(),
                "Notificação de sua próxima tarefa!",
                "notificacao",
                context
        );
    }

    /**
     * Envia um e-mail confirmando a criação de uma tarefa.
     * O envio ocorre automaticamente após a criação da tarefa, por meio de
     * uma mensagem Kafka recebida do microserviço agendador de tarefas.
     *
     * @param tarefaDTO dados da tarefa e informações do usuário destinatário
     */
    public void notificarTarefaCriada(TarefaDTO tarefaDTO){
        Context context = getContextTarefaCriada(tarefaDTO);
        sendEmail(
                tarefaDTO.emailUsuario(),
                "Criação de sua tarefa!",
                "tarefa-criada",
                context
        );
    }

    private Context getContextTarefa(TarefaDTO tarefaDTO){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Context context = criarContextoTarefa(
                tarefaDTO,
                formatter.format(tarefaDTO.dataEvento())
        );
        CitacaoDTO citacaoDTO = getCitacao();

        context.setVariable("citacao", citacaoDTO.quote());
        context.setVariable("autorCitacao", citacaoDTO.author());
        return context;
    }

    private Context getContextTarefaCriada(TarefaDTO tarefaDTO){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");
        return criarContextoTarefa(
                tarefaDTO,
                formatter.format(tarefaDTO.dataEvento())
        );
    }

    private Context criarContextoTarefa(TarefaDTO tarefaDTO, String dataEvento){
        Context context = new Context();
        context.setVariable("nomeTarefa", tarefaDTO.nomeTarefa());
        context.setVariable("dataEvento", dataEvento);
        context.setVariable("descricaoTarefa", tarefaDTO.descricaoTarefa());
        return context;
    }

    private void sendEmail(
            String emailEnviar,
            String subject,
            String templateName,
            Context context
    ){
        try{
            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                    mensagem, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(new InternetAddress(remetente, nomeRemetente));
            mimeMessageHelper.setTo(InternetAddress.parse(emailEnviar));
            mimeMessageHelper.setSubject(subject);

            String template = templateEngine.process(templateName, context);
            mimeMessageHelper.setText(template, true);
            javaMailSender.send(mensagem);

        } catch (MessagingException e) {
            throw new EmailException("Erro ao montar a mensagem de e-mail.", e);
        } catch (UnsupportedEncodingException e) {
            throw new EmailException("Erro na codificação.", e);
        } catch(Exception e){
            throw new EmailException("Erro ao enviar o e-mail.", e);
        }
    }

    private CitacaoDTO getCitacao(){
        return citacaoClient.buscarCitacao(keyApiCitacao).getFirst();
    }
}