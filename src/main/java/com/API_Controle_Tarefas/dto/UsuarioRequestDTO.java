package com.API_Controle_Tarefas.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "O nome do usuário não pode ser nulo ou vazio")
    @Size(max = 100, message = "O nome do usuário deve ter no máximo 100 caracteres")
    private String nome;

    @NotBlank(message = "O email do usuário não pode ser nulo ou vazio")
    @Email(message = "O email do usuário deve ser um email válido")
    private String email;

    @NotBlank(message = "A senha do usuário é obrigatória")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String senha;

    @Pattern(regexp = "^\\d{10,15}$", message = "Telefone deve conter entre 10 e 15 dígitos")
    private String telefone;
}
