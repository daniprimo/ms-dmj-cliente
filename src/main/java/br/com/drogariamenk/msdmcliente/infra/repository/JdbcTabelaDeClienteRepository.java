package br.com.drogariamenk.msdmcliente.infra.repository;

import br.com.drogariamenk.msdmcliente.aplicacao.exception.CpfNaoEncontradoException;
import br.com.drogariamenk.msdmcliente.aplicacao.exception.TelefoneNaoEncontradoException;
import br.com.drogariamenk.msdmcliente.dominio.Cliente;
import br.com.drogariamenk.msdmcliente.dto.RecuperandoCpfDTO;
import br.com.drogariamenk.msdmcliente.dto.mapper.BuscandoTodosOsClientesMapper;
import br.com.drogariamenk.msdmcliente.dto.mapper.ClienteMapper;
import br.com.drogariamenk.msdmcliente.dto.mapper.TelefoneMapper;
import br.com.drogariamenk.msdmcliente.infra.util.CriptografiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcTabelaDeClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CriptografiaService criptografiaService;

    public boolean isCpfExiste(String cpf) {
        List<RecuperandoCpfDTO> query = jdbcTemplate.query("SELECT cs.cpf FROM cliente cs;", new ClienteMapper());

        for (RecuperandoCpfDTO recuperandoCpfDTO : query) {
            String decrypt = criptografiaService.descriptografarEstaString(recuperandoCpfDTO.getCpf());
            if (cpf.equals(decrypt)) {
                    return true;
            }
        }


        return false;


    }

    public boolean isTelefonexiste(String telefone) {
        List<RecuperandoCpfDTO> query = jdbcTemplate.query("SELECT cs.telefone FROM cliente cs;", new TelefoneMapper());

        for (RecuperandoCpfDTO recuperandoCpfDTO : query) {
            String decrypt = criptografiaService.descriptografarEstaString(recuperandoCpfDTO.getCpf());
            if (telefone.equals(decrypt)) {
                return true;
            }
        }


        return false;


    }

    public Cliente buscarClientePeloCpf(String cpf) throws CpfNaoEncontradoException {
        List<Cliente> query = jdbcTemplate.query("SELECT * FROM cliente cs;", new BuscandoTodosOsClientesMapper());

        for (Cliente cliente : query) {
            String cpfDescriptografado = criptografiaService.descriptografarEstaString(cliente.getCpf());
            if (cpf.equals(cpfDescriptografado)) {
                return cliente.descriptografiaDosDadosSensiveis();
            }
        }

        throw new CpfNaoEncontradoException("CPF não consta em nossos registros.");


    }

    public Cliente buscarClientePeloTelefone(String telefone) throws TelefoneNaoEncontradoException {
        List<Cliente> query = jdbcTemplate.query("SELECT * FROM cliente cs;", new BuscandoTodosOsClientesMapper());

        for (Cliente cliente : query) {
            String telefoneDescriptografado = criptografiaService.descriptografarEstaString(cliente.getTelefone());
            if (telefone.equals(telefoneDescriptografado)) {
                return cliente.descriptografiaDosDadosSensiveis();
            }
        }

        throw new TelefoneNaoEncontradoException("Telefone não consta em nossos registros.");


    }

}
