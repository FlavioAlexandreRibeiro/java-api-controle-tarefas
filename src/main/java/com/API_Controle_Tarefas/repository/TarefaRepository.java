package com.API_Controle_Tarefas.repository;

import com.API_Controle_Tarefas.entity.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>,
        JpaSpecificationExecutor<Tarefa> {
}
