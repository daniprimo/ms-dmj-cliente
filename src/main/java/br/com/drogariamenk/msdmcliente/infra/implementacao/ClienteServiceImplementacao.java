package br.com.drogariamenk.msdmcliente.infra.implementacao;

import br.com.drogariamenk.msdmcliente.aplicacao.exception.*;
import br.com.drogariamenk.msdmcliente.aplicacao.service.ClienteService;
import br.com.drogariamenk.msdmcliente.dominio.CPF;
import br.com.drogariamenk.msdmcliente.dominio.Cliente;
import br.com.drogariamenk.msdmcliente.dominio.Endereco;
import br.com.drogariamenk.msdmcliente.dto.DadosNovoClienteCadastradoDTO;
import br.com.drogariamenk.msdmcliente.dto.request.AtualizarClienteRequest;
import br.com.drogariamenk.msdmcliente.dto.request.ClienteSalvarRequest;
import br.com.drogariamenk.msdmcliente.infra.mensageria.NovoClientePublisher;
import br.com.drogariamenk.msdmcliente.infra.repository.ClienteRepository;
import br.com.drogariamenk.msdmcliente.infra.repository.JdbcTabelaDeClienteRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImplementacao implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private JdbcTabelaDeClienteRepository jdbcClienteRepository;

    @Autowired
    private NovoClientePublisher novoClientePublisher;

    public Object listarTodos() {

        List<Cliente> all = clienteRepository.findAll();
        List<Cliente> list = all.stream().map(Cliente::descriptografiaDosDadosSensiveis).toList();
        return list;
    }

    @Override
    public Object salvarNovoCliente(ClienteSalvarRequest request)  {

        try {
            CPF cpf = new CPF(request.getCpf());
            verificarSeCpfEInexistente(request.getCpf());
            verificarSeTelefoneEInexistente(request.getTelefone());
        } catch (CpfInvalidoException | CpfJaExistenteException | TelefoneJaExistenteException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO",e.getMessage());
            return jsonObject.toString();
        }

        Endereco endereco = new Endereco(
                request.getCep(),
                request.getNumero(),
                request.getNomeDaRua(),
                request.getBairro());

        Cliente cliente = new Cliente();
            cliente.setNome(request.getNome());
            cliente.setCpf(request.getCpf());
            cliente.setTelefone(request.getTelefone());
            cliente.setEndereco(endereco);
            cliente.setComplementoDoEndereco(request.getComplemento());
            cliente.criptografiaDosDadosSensiveis();


        Cliente clienteSalvo = clienteRepository.save(cliente);
        clienteSalvo.descriptografiaDosDadosSensiveis(
                clienteSalvo.getCpf(),
                clienteSalvo.getTelefone()
        );

        DadosNovoClienteCadastradoDTO dadosNovoClienteCadastradoDTO = new DadosNovoClienteCadastradoDTO(
                clienteSalvo.getId(),
                clienteSalvo.getNome(),
                clienteSalvo.getCpf(),
                clienteSalvo.getTelefone(),
                "NOVO_CLIENTE"
            );
        try {
            novoClientePublisher.novoClienteCadastrado(dadosNovoClienteCadastradoDTO);
        } catch (JsonProcessingException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO ", "Objeto salvo mais não enviado no mensageria. "+e.getMessage());
            return jsonObject.toString();

        }


        return clienteSalvo;
    }

    @Override
    public Object buscarClientePeloCpf(String cpf) {
        try {
            return jdbcClienteRepository.buscarClientePeloCpf(cpf);
        } catch (CpfNaoEncontradoException e) {
            JSONObject jsonObject = new JSONObject();
            JSONObject json  = jsonObject.put("ERR: ", e.getMessage());
            return json.toString();
        }
    }

    @Override
    public Object buscarClientePeloTelefone(String telefone) {
        try {
            return jdbcClienteRepository.buscarClientePeloTelefone(telefone);
        } catch (TelefoneNaoEncontradoException e) {
            JSONObject jsonObject = new JSONObject();
            JSONObject json  = jsonObject.put("ERR: ", e.getMessage());
            return json.toString();
        }

    }

    @Override
    public Object atualizarClientePeloCpf(String cpf, AtualizarClienteRequest request) {
        Cliente cliente;
        try {
            verificarSeTelefoneEInexistente(request.getTelefone());
             cliente = jdbcClienteRepository.buscarClientePeloCpf(cpf);
        } catch (TelefoneJaExistenteException | CpfNaoEncontradoException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO",e.getMessage());
            return jsonObject.toString();
        }

        cliente.setNome(request.getNome());
        cliente.setTelefone(request.getTelefone());
        cliente.setComplementoDoEndereco(request.getComplementoDoEndereco());

        Endereco endereco = new Endereco(
                request.getCep(),
                request.getNumero(),
                request.getRua(),
                ""
        );
        cliente.setEndereco(endereco);
        cliente.criptografiaDosDadosSensiveis();

        Cliente clienteAtualizado = clienteRepository.save(cliente);
        clienteAtualizado.descriptografiaDosDadosSensiveis();

        DadosNovoClienteCadastradoDTO dadosNovoClienteCadastradoDTO = new DadosNovoClienteCadastradoDTO(
                clienteAtualizado.getId(),
                clienteAtualizado.getNome(),
                clienteAtualizado.getCpf(),
                clienteAtualizado.getTelefone(),

                "ATUALIZAR_CLIENTE");
        try {
            novoClientePublisher.novoClienteCadastrado(dadosNovoClienteCadastradoDTO);
        } catch (JsonProcessingException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO ", "Objeto salvo mais não enviado no mensageria. "+e.getMessage());
            return jsonObject.toString();

        }

        return clienteAtualizado;
    }

    @Override
    public Object atualizarClientePeloTelefone(String telefone, AtualizarClienteRequest request) {
        Cliente cliente;
        try {
            verificarSeTelefoneEInexistente(request.getTelefone());
            cliente = jdbcClienteRepository.buscarClientePeloTelefone(telefone);
        } catch (TelefoneJaExistenteException | TelefoneNaoEncontradoException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO",e.getMessage());
            return jsonObject.toString();
        }

        cliente.setNome(request.getNome());
        cliente.setTelefone(request.getTelefone());
        cliente.setComplementoDoEndereco(request.getComplementoDoEndereco());

        Endereco endereco = new Endereco(
                request.getCep(),
                request.getNumero(),
                request.getRua(),
                ""
        );
        cliente.setEndereco(endereco);
        cliente.criptografiaDosDadosSensiveis();

        Cliente clienteAtualizado = clienteRepository.save(cliente);
        clienteAtualizado.descriptografiaDosDadosSensiveis();

        DadosNovoClienteCadastradoDTO dadosNovoClienteCadastradoDTO = new DadosNovoClienteCadastradoDTO(
                clienteAtualizado.getId(),
                clienteAtualizado.getNome(),
                clienteAtualizado.getCpf(),
                clienteAtualizado.getTelefone(),
                "ATUALIZAR_CLIENTE");
        try {
            novoClientePublisher.novoClienteCadastrado(dadosNovoClienteCadastradoDTO);
        } catch (JsonProcessingException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO ", "Objeto salvo mais não enviado no mensageria. "+e.getMessage());
            return jsonObject.toString();

        }


        return clienteAtualizado;

    }

    @Override
    public Object deletarClientePeloCpf(String cpf) {

        Cliente cliente;
        try {
            cliente = jdbcClienteRepository.buscarClientePeloCpf(cpf);
        } catch (CpfNaoEncontradoException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO",e.getMessage());
            return jsonObject.toString();
        }

        clienteRepository.delete(cliente);
        DadosNovoClienteCadastradoDTO dadosNovoClienteCadastradoDTO = new DadosNovoClienteCadastradoDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                "DELETAR_CLIENTE");
        try {
            novoClientePublisher.novoClienteCadastrado(dadosNovoClienteCadastradoDTO);
        } catch (JsonProcessingException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO ", "Objeto salvo mais não enviado no mensageria. "+e.getMessage());
            return jsonObject.toString();

        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DELETE", "Cliente removido com sucesso.");
        return jsonObject.toString();

    }

    @Override
    public Object deletarClientePeloTelefone(String telefone) {
        Cliente cliente;
        try {
            cliente = jdbcClienteRepository.buscarClientePeloTelefone(telefone);
        } catch (TelefoneNaoEncontradoException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO",e.getMessage());
            return jsonObject.toString();
        }

        clienteRepository.delete(cliente);

        DadosNovoClienteCadastradoDTO dadosNovoClienteCadastradoDTO = new DadosNovoClienteCadastradoDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                "DELETAR_CLIENTE");
        try {
            novoClientePublisher.novoClienteCadastrado(dadosNovoClienteCadastradoDTO);
        } catch (JsonProcessingException e) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("ERRO ", "Objeto salvo mais não enviado no mensageria. "+e.getMessage());
            return jsonObject.toString();

        }



        JSONObject jsonObject = new JSONObject();
        jsonObject.put("DELETE", "Cliente removido com sucesso.");
        return jsonObject.toString();

    }


    private void verificarSeCpfEInexistente(String cpf) throws CpfJaExistenteException {

        if (jdbcClienteRepository.isCpfExiste(cpf)) {
            throw new CpfJaExistenteException("CPF já existe em nossos cadastros.");
        }


    }

    private void verificarSeTelefoneEInexistente(String telefone) throws TelefoneJaExistenteException {

        if (jdbcClienteRepository.isTelefonexiste(telefone)) {
            throw new TelefoneJaExistenteException("Telefone já existe em nossos cadastros.");
        }


    }


}
