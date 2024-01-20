package br.com.drogariamenk.msdmcliente.dto.mapper;

import br.com.drogariamenk.msdmcliente.dominio.Cliente;
import br.com.drogariamenk.msdmcliente.dto.RecuperandoCpfDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteMapper implements RowMapper<RecuperandoCpfDTO> {
    @Override
    public RecuperandoCpfDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        RecuperandoCpfDTO recuperandoCpfDTO = new RecuperandoCpfDTO();
        String cpf = "";
        cpf = rs.getString("cpf");
        recuperandoCpfDTO.setCpf(cpf);
        return recuperandoCpfDTO;
    }
}
