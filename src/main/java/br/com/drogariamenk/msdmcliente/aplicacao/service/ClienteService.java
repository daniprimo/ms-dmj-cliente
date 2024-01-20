package br.com.drogariamenk.msdmcliente.aplicacao.service;

import br.com.drogariamenk.msdmcliente.aplicacao.exception.CpfInvalidoException;
import br.com.drogariamenk.msdmcliente.aplicacao.exception.CpfJaExistenteException;
import br.com.drogariamenk.msdmcliente.dto.request.ClienteSalvarRequest;
import br.com.drogariamenk.msdmcliente.dto.response.ClienteSalvoResponse;
import org.springframework.stereotype.Service;

@Service
public interface ClienteService<T> {
    public T salvarNovoCliente(ClienteSalvarRequest request);

}
