package br.com.drogariamenk.msdmcliente.infra.util;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class CriptografiaService {

    BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();

    public CriptografiaService() {
        String MINHA_CHAVE = "DXTX";
        basicTextEncryptor.setPassword(MINHA_CHAVE);
    }

    public String criptografar(String string) {
        return basicTextEncryptor.encrypt(string);
    }


    public String descriptografar(String stringCriptografada) {
        return basicTextEncryptor.decrypt(stringCriptografada);
    }



}
