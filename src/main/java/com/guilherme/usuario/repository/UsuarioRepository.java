package com.guilherme.usuario.repository;

import com.guilherme.usuario.infra.entity.UsuarioConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioConta,Long> {
    Optional<UsuarioConta> findByEmail(String email);

    boolean existByEmail(String email);
    void deleteByEmail(String email);

}
