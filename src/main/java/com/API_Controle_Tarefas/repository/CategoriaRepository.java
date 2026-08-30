package com.API_Controle_Tarefas.repository;

import com.API_Controle_Tarefas.entity.Categoria;
import com.API_Controle_Tarefas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>
        {
            boolean existsByNome(String nome);

            boolean existsByNomeAndIdNot(String nome, Long id);


}
