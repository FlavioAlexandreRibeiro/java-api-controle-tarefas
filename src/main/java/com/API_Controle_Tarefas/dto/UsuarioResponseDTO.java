package com.API_Controle_Tarefas.dto;

import com.API_Controle_Tarefas.entity.Usuario;

import java.time.LocalDateTime;

public record UsuarioResponseDTO(

        Long id,
        String nome,
        String email,
        String telefone,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static UsuarioResponseDTO fromEntity(Usuario usuario) {
        return new UsuarioResponseDTO(

                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt()
        );
    }
}
