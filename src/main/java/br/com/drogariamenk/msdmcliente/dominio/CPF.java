package br.com.drogariamenk.msdmcliente.dominio;

import br.com.drogariamenk.msdmcliente.aplicacao.exception.CpfInvalidoException;
import br.com.drogariamenk.msdmcliente.infra.util.Validacoes;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Embeddable
@NoArgsConstructor
public class CPF {

    private String cpf;

    public CPF(String cpf) throws CpfInvalidoException {

        if (Validacoes.isCPF(cpf)) {
            this.cpf = cpf;
        } else {
            throw new CpfInvalidoException("CPF invalido.");
        }

    }
}
