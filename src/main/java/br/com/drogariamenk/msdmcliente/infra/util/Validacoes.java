package br.com.drogariamenk.msdmcliente.infra.util;

import br.com.drogariamenk.msdmcliente.aplicacao.exception.CpfJaExistenteException;
import br.com.drogariamenk.msdmcliente.dominio.CPF;
import br.com.drogariamenk.msdmcliente.dominio.Cliente;
import br.com.drogariamenk.msdmcliente.infra.repository.ClienteRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
public class Validacoes {

    public static boolean isCPF(String cpf) {

        String rg = "^\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}$";

        Pattern pattern = Pattern.compile(rg);
        Matcher matcher = pattern.matcher(cpf);

        if (matcher.find()) {
            return true;
        } else {
            return false;
        }

    }



}
