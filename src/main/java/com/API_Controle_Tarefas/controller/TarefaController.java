package com.API_Controle_Tarefas.controller;


import com.API_Controle_Tarefas.dto.TarefaRequestDTO;
import com.API_Controle_Tarefas.dto.TarefaResponseDTO;
import com.API_Controle_Tarefas.service.TarefaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/tarefa")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<TarefaResponseDTO> cadastrarTarefa(
            @Valid @RequestBody TarefaRequestDTO tarefaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tarefaService.cadastrarTarefa(tarefaDTO));
    }

    @GetMapping
    public ResponseEntity<Page<TarefaResponseDTO>> buscarTodos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) LocalDateTime dataVencimento,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String prioridade,
            @RequestParam(required = false) Long usuarioId,
            @RequestParam(required = false) Long categoriaId,
            Pageable pageable) {
        return ResponseEntity.ok(tarefaService.buscasTodos(
                titulo, descricao, dataVencimento, status, prioridade, usuarioId, categoriaId, pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> buscarPorId(
            @Positive @PathVariable Long id) {
        return ResponseEntity.ok(tarefaService.buscarPorId(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<TarefaResponseDTO> atualizarTarefa(
            @Positive @PathVariable Long id,
            @Valid @RequestBody TarefaRequestDTO tarefaDTO) {
        return ResponseEntity.ok(tarefaService.atualizarTarefa(id, tarefaDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTarefa(
            @Positive @PathVariable Long id) {
        tarefaService.excluirTarefa(id);
        return ResponseEntity.noContent().build();
    }
}
