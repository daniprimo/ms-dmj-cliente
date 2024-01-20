package br.com.drogariamenk.msdmcliente.infra.repository;

import br.com.drogariamenk.msdmcliente.dominio.CPF;
import br.com.drogariamenk.msdmcliente.dominio.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
