package br.com.drogariamenk.msdmcliente.dto.mapper;

import br.com.drogariamenk.msdmcliente.dto.RecuperandoCpfDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TelefoneMapper implements RowMapper<RecuperandoCpfDTO> {
    @Override
    public RecuperandoCpfDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        RecuperandoCpfDTO recuperandoCpfDTO = new RecuperandoCpfDTO();
        String telefone = "";
        telefone = rs.getString("telefone");
        recuperandoCpfDTO.setCpf(telefone);
        return recuperandoCpfDTO;
    }
}
