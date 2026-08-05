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

    public void enviarEmail(TarefaDTO tarefaDTO){
        try{
            MimeMessage mensagem = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                    mensagem, true, StandardCharsets.UTF_8.name());

            mimeMessageHelper.setFrom(new InternetAddress(remetente, nomeRemetente));
            mimeMessageHelper.setTo(InternetAddress.parse(tarefaDTO.getEmailUsuario()));
            mimeMessageHelper.setSubject("Notificação de Tarefa");
            CitacaoDTO citacaoDTO = getCitacao();

            // Setando variáveis do template
            Context context = new Context();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String dataEvento = formatter.format(tarefaDTO.getDataEvento());

            context.setVariable("nomeTarefa", tarefaDTO.getNomeTarefa());
            context.setVariable("dataEvento", dataEvento);
            context.setVariable("descricaoTarefa", tarefaDTO.getDescricaoTarefa());
            context.setVariable("citacao", citacaoDTO.getQuote());
            context.setVariable("autorCitacao", citacaoDTO.getAuthor());

            String template = templateEngine.process("notificacao", context);
            mimeMessageHelper.setText(template, true);
            javaMailSender.send(mensagem);

        } catch (MessagingException e) {
            throw new EmailException("Erro ao montar a mensagem de e-mail.", e);
        } catch (UnsupportedEncodingException e) {
            throw new EmailException("Erro na codificação.", e);
        } catch (Exception e) {
            throw new EmailException("Erro ao enviar o e-mail.", e);
        }
    }

    public CitacaoDTO getCitacao(){
        return citacaoClient.buscarCitacao(keyApiCitacao).getFirst();
    }
}