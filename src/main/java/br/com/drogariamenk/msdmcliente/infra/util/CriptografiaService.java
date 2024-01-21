package br.com.drogariamenk.msdmcliente.infra.util;

import lombok.NoArgsConstructor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class CriptografiaService {

    private final BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();

    public CriptografiaService() {
        String MINHA_CHAVE = "DXTX";
        basicTextEncryptor.setPassword(MINHA_CHAVE);
    }

    public String criptografarEstaString(String string) {
        return basicTextEncryptor.encrypt(string);

    }

    public String descriptografarEstaString(String string) {
        return basicTextEncryptor.decrypt(string);

    }




}
