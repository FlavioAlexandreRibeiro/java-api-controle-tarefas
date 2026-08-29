package com.API_Controle_Tarefas.dto;

import com.API_Controle_Tarefas.entity.Categoria;

import java.time.LocalDateTime;

public record CategoriaResponseDTO(

        Long id,
        String nome,
        String descricao,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static CategoriaResponseDTO fromEntity(Categoria categoria) {
        return new CategoriaResponseDTO(

                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao(),
                categoria.getCreatedAt(),
                categoria.getUpdatedAt()
        );
    }
}
