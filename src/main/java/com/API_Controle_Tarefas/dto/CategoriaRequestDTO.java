package com.API_Controle_Tarefas.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRequestDTO {


    @NotBlank(message = "O nome da categoria não pode ser nulo ou vazio")
    @Size(min = 3, max = 100, message = "O nome da categoria deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "A descrição da categoria não pode ser nula ou vazia")
    @Size(max = 255, message = "A descrição da categoria deve ter no máximo 255 caracteres")
    private String descricao;
}
