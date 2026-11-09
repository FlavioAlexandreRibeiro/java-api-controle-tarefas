package com.API_Controle_Tarefas.dto;

import com.API_Controle_Tarefas.entity.Prioridade;
import com.API_Controle_Tarefas.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TarefaRequestDTO {

    @NotBlank(message = "O titulo da tarefa não pode ser nulo ou vazio")
    @Size(min = 3, max = 100, message = "O titulo da tarefa deve ter entre 3 e 100 caracteres")
    private String titulo;

    @NotBlank(message = "A descrição da tarefa não pode ser nula ou vazia")
    @Size(max = 255, message = "A descrição da tarefa deve ter no máximo 255 caracteres")
    private String descricao;

    @NotNull(message = "O status da tarefa não pode ser nulo")
    private Status status;

    @NotNull(message = "A prioridade da tarefa não pode ser nula")
    private Prioridade prioridade;

    @NotNull(message = "A data de vencimento da tarefa não pode ser nula")
    private LocalDate dataVencimento;

    @NotNull(message = "ID do usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "ID da categoria é obrigatório")
    private Long categoriaId;
}
