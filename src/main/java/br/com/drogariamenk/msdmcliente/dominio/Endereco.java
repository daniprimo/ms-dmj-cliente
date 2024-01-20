package br.com.drogariamenk.msdmcliente.dominio;

import br.com.drogariamenk.msdmcliente.aplicacao.exception.ViaCepForaDoArException;
import br.com.drogariamenk.msdmcliente.infra.externos.ApiExterna;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
public class Endereco {

    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String localidade;
    private String uf;
    public Endereco(String cep, String numero, String nomeDarua, String bairro) {
        Endereco endereco = null;
        try {
            endereco = buscandoInfosDoEndereco(cep);
            this.cep = endereco.getCep();
            this.logradouro = endereco.getLogradouro();
            this.numero = numero;
            this.bairro = endereco.getBairro();
            this.localidade = endereco.getLocalidade();
            this.uf = endereco.getUf();
        } catch (ViaCepForaDoArException e) {
            this.cep = cep;
            this.logradouro = nomeDarua;
            this.numero = numero;
            this.bairro = bairro;

        }
    }

    public Endereco(String cep, String nomeDarua, String bairro) {
        Endereco endereco = null;
        try {
            endereco = buscandoInfosDoEndereco(cep);
             this.cep = endereco.getCep();
            this.logradouro = endereco.getLogradouro();
             this.bairro = endereco.getBairro();
            this.localidade = endereco.getLocalidade();
            this.uf = endereco.getUf();
        } catch (ViaCepForaDoArException e) {
            this.cep = cep;
            this.logradouro = nomeDarua;
            this.bairro = bairro;
        }

    }

    private Endereco buscandoInfosDoEndereco(String cep) throws ViaCepForaDoArException {
        return ApiExterna
                .consumindoViaCep(cep);
    }
}
