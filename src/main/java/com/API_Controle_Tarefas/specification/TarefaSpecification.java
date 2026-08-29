package com.API_Controle_Tarefas.specification;

import com.API_Controle_Tarefas.entity.Prioridade;
import com.API_Controle_Tarefas.entity.Status;
import com.API_Controle_Tarefas.entity.Tarefa;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TarefaSpecification {

    public static Specification<Tarefa> porTitulo(String titulo) {
        return (root, query, criteriaBuilder) -> {
            if (titulo == null || titulo.trim().isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("titulo")),
                    "%" + titulo.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Tarefa> porDescricao(String descricao) {
        return (root, query, criteriaBuilder) -> {
            if (descricao == null || descricao.trim().isEmpty()) {
                return null;
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("descricao")),
                    "%" + descricao.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Tarefa> porDataVencimento(LocalDate dataVencimento) {
        return (root, query, criteriaBuilder) -> {
            if (dataVencimento == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("dataVencimento"), dataVencimento);
        };
    }

    public static Specification<Tarefa> porUsuarioId(Long usuarioId) {
        return (root, query, criteriaBuilder) -> {
            if (usuarioId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.join("usuario").get("id"), usuarioId);
        };
    }

    public static Specification<Tarefa> porCategoriaId(Long categoriaId) {
        return (root, query, criteriaBuilder) -> {
            if (categoriaId == null) {
                return null;
            }
            return criteriaBuilder.equal(root.join("categoria").get("id"), categoriaId);
        };
    }

    public static Specification<Tarefa> porStatus(Status status) {
        return (root, query, criteriaBuilder) -> {
            if (status == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("status"), status);
        };
    }

    public static Specification<Tarefa> porPrioridade(Prioridade prioridade) {
        return (root, query, criteriaBuilder) -> {
            if (prioridade == null) {
                return null;
            }
            return criteriaBuilder.equal(root.get("prioridade"), prioridade);
        };
    }
}