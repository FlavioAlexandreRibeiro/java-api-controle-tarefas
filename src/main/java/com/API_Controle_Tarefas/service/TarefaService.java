package com.API_Controle_Tarefas.service;


import com.API_Controle_Tarefas.dto.TarefaRequestDTO;
import com.API_Controle_Tarefas.dto.TarefaResponseDTO;
import com.API_Controle_Tarefas.entity.*;
import com.API_Controle_Tarefas.exception.ResourceNotFoundException;
import com.API_Controle_Tarefas.repository.CategoriaRepository;
import com.API_Controle_Tarefas.repository.TarefaRepository;
import com.API_Controle_Tarefas.repository.UsuarioRepository;
import com.API_Controle_Tarefas.specification.TarefaSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;

    public TarefaResponseDTO cadastrarTarefa(TarefaRequestDTO tarefaDTO) {

        Usuario usuario = usuarioRepository.findById(tarefaDTO.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", tarefaDTO.getUsuarioId()));

        Categoria categoria = categoriaRepository.findById(tarefaDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", tarefaDTO.getCategoriaId()));

        Tarefa tarefa = new Tarefa();

        tarefa.setTitulo(tarefaDTO.getTitulo());
        tarefa.setDescricao(tarefaDTO.getDescricao());
        tarefa.setDataVencimento(tarefaDTO.getDataVencimento());
        tarefa.setPrioridade(tarefaDTO.getPrioridade());
        tarefa.setUsuario(usuario);
        tarefa.setCategoria(categoria);
        tarefa.setCreatedAt(LocalDateTime.now());
        tarefa.setUpdatedAt(LocalDateTime.now());

        tarefa = tarefaRepository.save(tarefa);
        return TarefaResponseDTO.fromEntity(tarefa);
    }

    public Page<TarefaResponseDTO> buscasTodos(
            String titulo,
            String descricao,
            LocalDateTime dataVencimento,
            String status,
            String prioridade,
            Long usuarioId,
            Long categoriaId,
            Pageable pageable) {

        Specification<Tarefa> spec = ((root, query,
                                       criteriaBuilder) -> criteriaBuilder.conjunction());

        spec = spec.and(TarefaSpecification.porTitulo(titulo));
        spec = spec.and(TarefaSpecification.porDescricao(descricao));
        spec = spec.and(TarefaSpecification.porStatus(status));
        spec = spec.and(TarefaSpecification.porDataVencimento(LocalDate.from(dataVencimento)));
        spec = spec.and(TarefaSpecification.porPrioridade(prioridade));
        spec = spec.and(TarefaSpecification.porUsuarioId(usuarioId));
        spec = spec.and(TarefaSpecification.porCategoriaId(categoriaId));

        return tarefaRepository.findAll(spec, pageable)
                .map(TarefaResponseDTO::fromEntity);
    }


    public TarefaResponseDTO buscarPorId(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa", id));
        return TarefaResponseDTO.fromEntity(tarefa);
    }


    public TarefaResponseDTO atualizarTarefa(Long id, TarefaRequestDTO tarefaDTO) {

        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa", id));

        tarefa.setTitulo(tarefaDTO.getTitulo());
        tarefa.setDescricao(tarefaDTO.getDescricao());
        tarefa.setDataVencimento(tarefaDTO.getDataVencimento());
        tarefa.setPrioridade(tarefaDTO.getPrioridade());
        tarefa.setUpdatedAt(LocalDateTime.now());

        tarefa = tarefaRepository.save(tarefa);
        return TarefaResponseDTO.fromEntity(tarefa);
    }

    public void excluirTarefa(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa", id));
        tarefaRepository.delete(tarefa);
    }
}




