
package notificacaotarefas.notificacao.infraestructure.messaging.config;

import notificacaotarefas.notificacao.business.dto.TarefaDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ConsumerKafkaConfig {

    @Bean
    public ConsumerFactory<String, TarefaDTO> consumerFactory() {
        Map<String, Object> mapConfigs = new HashMap<>();

        mapConfigs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        mapConfigs.put(ConsumerConfig.GROUP_ID_CONFIG, "notificacao-group");
        mapConfigs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(
                mapConfigs,
                new StringDeserializer(),
                new JacksonJsonDeserializer<>(TarefaDTO.class)
                        .trustedPackages("notificacaotarefas.notificacao.business.dto")
                        .ignoreTypeHeaders()
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, TarefaDTO> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, TarefaDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}