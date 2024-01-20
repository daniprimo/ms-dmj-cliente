package br.com.drogariamenk.msdmcliente.infra.repository;

import br.com.drogariamenk.msdmcliente.dto.RecuperandoCpfDTO;
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
        String MINHA_CHAVE = "DXTX";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(MINHA_CHAVE);
        List<RecuperandoCpfDTO> query = jdbcTemplate.query("SELECT cs.cpf FROM cliente cs;", new ClienteMapper());

        for (RecuperandoCpfDTO recuperandoCpfDTO : query) {
            String decrypt = basicTextEncryptor.decrypt(recuperandoCpfDTO.getCpf());
            if (cpf.equals(decrypt)) {
                    return true;
            }
        }


        return false;


    }

    public boolean isTelefonexiste(String telefone) {
        String MINHA_CHAVE = "DXTX";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(MINHA_CHAVE);
        List<RecuperandoCpfDTO> query = jdbcTemplate.query("SELECT cs.telefone FROM cliente cs;", new TelefoneMapper());

        for (RecuperandoCpfDTO recuperandoCpfDTO : query) {
            String decrypt = basicTextEncryptor.decrypt(recuperandoCpfDTO.getCpf());
            if (telefone.equals(decrypt)) {
                return true;
            }
        }


        return false;


    }
}
