package br.com.drogariamenk.msdmcliente.config.mensageria;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {


    @Value("${mq.queues.emissao-cartoes}")
    private String novoCliente;

    @Bean
    public Queue queueNovoClienteCadastrado() {
        return new Queue(novoCliente, true);
    }

}
