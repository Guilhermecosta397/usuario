package com.guilherme.usuario.repository;

import com.guilherme.usuario.infra.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

}
