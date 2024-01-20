package br.com.drogariamenk.msdmcliente.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ClienteSalvoResponse {

    private Long idDoCliente;
    private String mensagem;
}
