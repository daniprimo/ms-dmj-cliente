package br.com.drogariamenk.msdmcliente.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DadosNovoClienteCadastradoDTO {

    private Long idDoCliente;
    private String nomeDoCliente;

    private String cpfDoCliente;

    private String telefoneDoCliente;

    private String tipoDeAcao;

}
