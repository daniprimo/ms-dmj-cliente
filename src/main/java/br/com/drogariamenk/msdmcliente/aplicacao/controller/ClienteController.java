package br.com.drogariamenk.msdmcliente.aplicacao.controller;

import br.com.drogariamenk.msdmcliente.aplicacao.service.ClienteService;
import br.com.drogariamenk.msdmcliente.dto.request.AtualizarClienteRequest;
import br.com.drogariamenk.msdmcliente.dto.request.ClienteSalvarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.DELETE;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

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

    @GetMapping("/todos")
    public  ResponseEntity buscarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @PutMapping("atualizarClientePeloCpf/{cpf}")
    public ResponseEntity atualizarClientePeloCpf(
            @PathVariable String cpf,
            @RequestBody AtualizarClienteRequest request
            ){
        return ResponseEntity.ok(clienteService.atualizarClientePeloCpf(cpf, request));
    }

    @PutMapping("atualizarClientePeloTelefone/{telefone}")
    public ResponseEntity atualizarClientePeloTelefone(
            @PathVariable String telefone,
            @RequestBody AtualizarClienteRequest request
            ){
        return ResponseEntity.ok(clienteService
                .atualizarClientePeloTelefone(telefone, request));
    }

    @DeleteMapping("deletarClientePeloCpf/{cpf}")
    public ResponseEntity removerClientePeloCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(clienteService.deletarClientePeloCpf(cpf));
    }

    @DeleteMapping("deletarClientePeloTelefone/{telefone}")
    public ResponseEntity removerClientePeloTelefone(@PathVariable String telefone) {
        return ResponseEntity.ok(clienteService
                .deletarClientePeloTelefone(telefone));
    }




}
