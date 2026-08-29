package com.API_Controle_Tarefas.dto;

import com.API_Controle_Tarefas.entity.Prioridade;
import com.API_Controle_Tarefas.entity.Status;
import com.API_Controle_Tarefas.entity.Tarefa;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TarefaResponseDTO(

        Long id,
        String titulo,
        String descricao,
        Status status,
        Prioridade prioridade,
        LocalDate dataVencimento,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {

    public static TarefaResponseDTO fromEntity(Tarefa tarefa) {
        return new TarefaResponseDTO(

                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getStatus(),
                tarefa.getPrioridade(),
                tarefa.getDataVencimento(),
                tarefa.getCreatedAt(),
                tarefa.getUpdatedAt()
        );
    }
}
