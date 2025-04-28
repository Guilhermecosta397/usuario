package com.guilherme.usuario.repository;

import com.guilherme.usuario.infra.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository extends JpaRepository<Telefone,Long> {
}
