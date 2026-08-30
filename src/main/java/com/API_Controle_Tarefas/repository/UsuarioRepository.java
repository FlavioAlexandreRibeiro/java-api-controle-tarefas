package com.API_Controle_Tarefas.repository;

import com.API_Controle_Tarefas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByNome(String nome);

    boolean existsByEmail(String email);

    boolean existsByNomeAndIdNot(String nome, Long id);

    boolean existsByEmailAndIdNot(String email, Long id);
}
