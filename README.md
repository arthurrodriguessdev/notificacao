# Notificador

Microsserviço responsável por enviar notificações por e-mail sobre tarefas. Ele integra o sistema de tarefas por meio de Kafka, desacoplando o agendamento do envio das mensagens.

## Funcionalidades

- Consome eventos JSON do tópico Kafka `tarefa-criada` e envia um e-mail confirmando a criação da tarefa.
- Disponibiliza o endpoint `POST /email` para enviar uma notificação sobre uma próxima tarefa. Essa mensagem usa a API de citações para incluir uma citação no e-mail.
- Renderiza os e-mails com templates Thymeleaf: `tarefa-criada.html` para tarefas criadas e `notificacao.html` para notificações de próximas tarefas.

## Fluxo Kafka

O serviço consumidor espera mensagens JSON neste formato:

```json
{
  "nomeTarefa": "Revisar projeto",
  "descricaoTarefa": "Revisar as pendências da semana",
  "dataEvento": "25-10-2026 16:30:00",
  "emailUsuario": "usuario@example.com"
}
```

O campo `dataEvento` deve seguir o formato `dd-MM-yyyy HH:mm:ss`. O tópico padrão é `tarefa-criada`, e o grupo padrão do consumidor é `notificacao-group`. Ambos, assim como o endereço do broker, podem ser configurados por variáveis de ambiente.

## Configuração

Configure as variáveis abaixo antes de iniciar a aplicação. Credenciais não devem ser armazenadas no repositório.

| Variável | Obrigatória | Padrão / finalidade |
| --- | --- | --- |
| `USERNAME_EMAIL_NOTIFICACAO` | Sim | Usuário da conta SMTP remetente |
| `PASSWORD_EMAIL_NOTIFICACAO` | Sim | Senha ou senha de aplicativo SMTP |
| `KEY_API_CITACAO` | Sim | Chave da API de citações usada pelo endpoint `POST /email` |
| `NOME_REMETENTE_EMAIL_NOTIFICACAO` | Não | Nome exibido do remetente (`Notificador`) |
| `HOST_EMAIL_NOTIFICACAO` | Não | Servidor SMTP (`smtp.gmail.com`) |
| `PORTA_EMAIL_NOTIFICACAO` | Não | Porta SMTP (`587`) |
| `URL_API_CITACAO` | Não | URL base da API (`https://api.api-ninjas.com`) |
| `KAFKA_BOOTSTRAP_SERVERS` | Não | Brokers Kafka (`localhost:9092`) |
| `KAFKA_CONSUMER_GROUP_ID` | Não | Grupo consumidor (`notificacao-group`) |
| `KAFKA_TASK_CREATED_TOPIC` | Não | Tópico de criação de tarefas (`tarefa-criada`) |
| `PORTA_NOTIFICACAO` | Não | Porta HTTP (`8082`) |

## Execução local

Requisitos: Java 21, Kafka acessível e uma conta SMTP configurada.

```powershell
$env:USERNAME_EMAIL_NOTIFICACAO = "seu-email"
$env:PASSWORD_EMAIL_NOTIFICACAO = "sua-senha-de-aplicativo"
$env:KEY_API_CITACAO = "sua-chave"
.\gradlew.bat bootRun
```

O consumidor conecta por padrão a `localhost:9092`. Para usar outro broker, configure `KAFKA_BOOTSTRAP_SERVERS`.

## Build e Docker

```powershell
.\gradlew.bat build
docker build -t notificacao .
docker run --rm -p 8082:8082 `
  -e USERNAME_EMAIL_NOTIFICACAO `
  -e PASSWORD_EMAIL_NOTIFICACAO `
  -e KEY_API_CITACAO `
  -e KAFKA_BOOTSTRAP_SERVERS `
  notificacao
```

Para enviar uma notificação de próxima tarefa via HTTP, faça `POST /email` com o mesmo JSON descrito acima.

## Tecnologias

Java 21, Spring Boot, Spring Kafka, Spring Web, Spring Mail, Thymeleaf, OpenFeign, Gradle e Docker.

## Autor

- [Arthur Rodrigues](https://www.linkedin.com/in/arthur-rodriguesx/)
