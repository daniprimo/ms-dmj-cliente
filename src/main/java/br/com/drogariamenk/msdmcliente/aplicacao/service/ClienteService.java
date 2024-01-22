package br.com.drogariamenk.msdmcliente.aplicacao.service;

import br.com.drogariamenk.msdmcliente.aplicacao.exception.CpfInvalidoException;
import br.com.drogariamenk.msdmcliente.aplicacao.exception.CpfJaExistenteException;
import br.com.drogariamenk.msdmcliente.dto.request.AtualizarClienteRequest;
import br.com.drogariamenk.msdmcliente.dto.request.ClienteSalvarRequest;
import br.com.drogariamenk.msdmcliente.dto.response.ClienteSalvoResponse;
import org.springframework.stereotype.Service;

@Service
public interface ClienteService<T> {
    public T salvarNovoCliente(ClienteSalvarRequest request);
    public T buscarClientePeloCpf(String cpf);
    public T buscarClientePeloTelefone(String telefone);
    public T atualizarClientePeloCpf(String cpf, AtualizarClienteRequest request);
    public T atualizarClientePeloTelefone(String telefone, AtualizarClienteRequest request);
    public T deletarClientePeloCpf(String cpf);
    public T deletarClientePeloTelefone(String telefone);

    public T listarTodos();

}
