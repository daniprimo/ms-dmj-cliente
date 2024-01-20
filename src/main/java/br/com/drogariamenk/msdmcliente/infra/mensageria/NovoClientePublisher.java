package br.com.drogariamenk.msdmcliente.infra.mensageria;

import br.com.drogariamenk.msdmcliente.dto.DadosNovoClienteCadastradoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class NovoClientePublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueNovoCliente;

    public void novoClienteCadastrado(DadosNovoClienteCadastradoDTO dados) throws JsonProcessingException {
        var json = convertToJson(dados);
        rabbitTemplate.convertAndSend(queueNovoCliente.getName(), json);
    }

    private String convertToJson(DadosNovoClienteCadastradoDTO dados) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(dados);
        return json;
    }

}
