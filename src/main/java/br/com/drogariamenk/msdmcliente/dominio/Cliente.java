package br.com.drogariamenk.msdmcliente.dominio;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jasypt.util.text.BasicTextEncryptor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String telefone;

    @Embedded
    private Endereco endereco;

    private String complementoDoEndereco;


    public void criptografiaDosDadosSensiveis() {
        String MINHA_CHAVE = "DXTX";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(MINHA_CHAVE);
        String cpfCriptografado = basicTextEncryptor.encrypt(getCpf());
        String telefoneCriptogarfado = basicTextEncryptor.encrypt(getTelefone());

        this.cpf = cpfCriptografado;
        this.telefone = telefoneCriptogarfado;
    }

    public void descriptografiaDosDadosSensiveis(String cpfCriptografado, String telefoneCriptografado) {
        String MINHA_CHAVE = "DXTX";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(MINHA_CHAVE);
        String cpfDesriptografado = basicTextEncryptor.decrypt(cpfCriptografado);
        String telefoneDescriptogarfado = basicTextEncryptor.decrypt(telefoneCriptografado);

        this.cpf = cpfDesriptografado;
        this.telefone = telefoneDescriptogarfado;
    }


}
