package com.API_Controle_Tarefas.controller;


import com.API_Controle_Tarefas.dto.UsuarioRequestDTO;
import com.API_Controle_Tarefas.dto.UsuarioResponseDTO;
import com.API_Controle_Tarefas.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.cadastrarUsuario(usuarioDTO));
    }


    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(usuarioService.buscarTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(
            @Positive @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(
            @Positive @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, usuarioDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(
            @Positive @PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
