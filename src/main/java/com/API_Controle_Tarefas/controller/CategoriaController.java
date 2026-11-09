package com.API_Controle_Tarefas.controller;

import com.API_Controle_Tarefas.dto.CategoriaRequestDTO;
import com.API_Controle_Tarefas.dto.CategoriaResponseDTO;
import com.API_Controle_Tarefas.service.CategoriaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> cadastrarCategoria(
            @Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaService.cadastrarCategoria(categoriaDTO));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(categoriaService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(
            @Positive @PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizarCategoria(
            @Positive @PathVariable Long id,
            @Valid @RequestBody CategoriaRequestDTO categoriaDTO) {
        return ResponseEntity.ok(categoriaService.atualizarCategoria(id, categoriaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCategoria(
           @Positive @PathVariable Long id) {
        categoriaService.excluirCategoria(id);
        return ResponseEntity.noContent().build();
    }
}
