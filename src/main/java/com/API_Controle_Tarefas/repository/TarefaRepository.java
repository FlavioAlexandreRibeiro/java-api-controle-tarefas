package com.API_Controle_Tarefas.repository;

import com.API_Controle_Tarefas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TarefaRepository extends JpaRepository<Usuario, Long>,
        JpaSpecificationExecutor<Usuario> {
}
