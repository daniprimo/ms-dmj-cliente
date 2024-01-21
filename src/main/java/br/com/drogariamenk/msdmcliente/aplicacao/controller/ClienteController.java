package br.com.drogariamenk.msdmcliente.aplicacao.controller;

import br.com.drogariamenk.msdmcliente.aplicacao.exception.CpfInvalidoException;
import br.com.drogariamenk.msdmcliente.aplicacao.exception.CpfJaExistenteException;
import br.com.drogariamenk.msdmcliente.aplicacao.service.ClienteService;
import br.com.drogariamenk.msdmcliente.dto.request.ClienteSalvarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @GetMapping
    public String testando () {
        return "ok";
    }

    @PostMapping()
    public ResponseEntity salvarNovoCliente (@RequestBody ClienteSalvarRequest cliente){
        Object clienteSalvo = clienteService.salvarNovoCliente(cliente);
        return ResponseEntity.ok(clienteSalvo);
    }

    @GetMapping("/buscarPeloCpf/{cpf}")
    public ResponseEntity buscarClientePeloCpf(@PathVariable String cpf) {
        Object clienteEncontrado = clienteService.buscarClientePeloCpf(cpf);
        return ResponseEntity.ok(clienteEncontrado);
    }

    @GetMapping("/buscarPeloTelefone/{telefone}")
    public ResponseEntity buscarClientePeloTelefone(@PathVariable String telefone) {
        Object clienteEncontrado = clienteService.buscarClientePeloTelefone(telefone);
        return ResponseEntity.ok(clienteEncontrado);
    }

}
