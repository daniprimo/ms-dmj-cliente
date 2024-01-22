package br.com.drogariamenk.msdmcliente.infra.repository;

import br.com.drogariamenk.msdmcliente.aplicacao.exception.CpfNaoEncontradoException;
import br.com.drogariamenk.msdmcliente.aplicacao.exception.TelefoneNaoEncontradoException;
import br.com.drogariamenk.msdmcliente.dominio.Cliente;
import br.com.drogariamenk.msdmcliente.dto.RecuperandoCpfDTO;
import br.com.drogariamenk.msdmcliente.dto.mapper.BuscandoTodosOsClientesMapper;
import br.com.drogariamenk.msdmcliente.dto.mapper.ClienteMapper;
import br.com.drogariamenk.msdmcliente.dto.mapper.TelefoneMapper;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTabelaDeClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public boolean isCpfExiste(String cpf) {



        List<RecuperandoCpfDTO> query = jdbcTemplate.query("SELECT cs.cpf FROM cliente cs;", new ClienteMapper());

        for (RecuperandoCpfDTO recuperandoCpfDTO : query) {
            if (cpf.equals(recuperandoCpfDTO.getCpf())) {
                    return true;
            }
        }


        return false;


    }

    public boolean isTelefonexiste(String telefone) {



        List<RecuperandoCpfDTO> query = jdbcTemplate.query("SELECT cs.telefone FROM cliente cs;", new TelefoneMapper());

        for (RecuperandoCpfDTO recuperandoCpfDTO : query) {
            if (telefone.equals(recuperandoCpfDTO.getCpf())) {
                return true;
            }
        }


        return false;


    }

    public Cliente buscarClientePeloCpf(String cpf) throws CpfNaoEncontradoException {

        List<Cliente> query = jdbcTemplate.query("SELECT * FROM cliente cs;", new BuscandoTodosOsClientesMapper());

        for (Cliente cliente : query) {
            Cliente clienteDescriptografado = cliente.descriptografiaDosDadosSensiveis();
            if (cpf.equals(clienteDescriptografado.getCpf())) {
                return cliente;
            }
        }

        throw new CpfNaoEncontradoException("CPF não consta em nossos registros.");


    }

    public Cliente buscarClientePeloTelefone(String telefone) throws TelefoneNaoEncontradoException {

        List<Cliente> query = jdbcTemplate.query("SELECT * FROM cliente cs;", new BuscandoTodosOsClientesMapper());

        for (Cliente cliente : query) {
            Cliente clienteDescriptografado = cliente.descriptografiaDosDadosSensiveis();
            if (telefone.equals(clienteDescriptografado.getTelefone())) {
                return cliente;
            }
        }

        throw new TelefoneNaoEncontradoException("Telefone não consta em nossos registros.");


    }

}
