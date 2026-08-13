
# Notificador

Microsserviço responsável pelo envio de notificações por e-mail sobre tarefas agendadas.

O Notificador recebe informações de tarefas agendadas por meio da comunicação com outro microsserviço e identifica aquelas que estão programadas para a próxima hora. A partir dessas informações, realiza o envio de um e-mail para notificar o usuário sobre a tarefa.

O projeto faz parte de uma arquitetura baseada em microsserviços, utilizando comunicação entre aplicações e APIs externas por meio de OpenFeign.

## Tecnologias
- Java
- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL
- OpenFeign
- Docker
- Gradle
- SonarQube

## Docker
O projeto possui um Dockerfile para facilitar a criação da imagem e a execução da aplicação em um container.

## Qualidade
No quesito qualidade de código, o **SonarQube** foi utilizado como uma ferramenta para analisar e identificar possíveis erros (code smells) e bugs que poderiam atrapalhar a execução da aplicação.

## Objetivo

O principal objetivo do Notificador é desacoplar o processo de envio de notificações do microsserviço responsável pelo agendamento das tarefas.

Dessa forma, cada serviço possui uma responsabilidade bem definida, facilitando:

- Manutenção
- Evolução independente dos serviços
- Escalabilidade
- Organização da arquitetura
- Integração entre diferentes componentes do sistema

## Autor

- [Arthur Rodrigues](https://www.linkedin.com/in/arthur-rodriguesx/)
