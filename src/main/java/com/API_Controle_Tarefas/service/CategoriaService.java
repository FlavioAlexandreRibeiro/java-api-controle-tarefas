package com.API_Controle_Tarefas.service;


import com.API_Controle_Tarefas.dto.CategoriaRequestDTO;
import com.API_Controle_Tarefas.dto.CategoriaResponseDTO;
import com.API_Controle_Tarefas.entity.Categoria;
import com.API_Controle_Tarefas.exception.DuplicateResourceException;
import com.API_Controle_Tarefas.exception.ResourceNotFoundException;
import com.API_Controle_Tarefas.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaResponseDTO cadastrarCategoria(CategoriaRequestDTO categoriaDTO) {

        verificarNomeDuplicado(categoriaDTO.getNome());

        Categoria categoria = new Categoria();
        categoria.setNome(categoriaDTO.getNome());
        categoria.setDescricao(categoriaDTO.getDescricao());
        categoria.setCreatedAt(LocalDateTime.now());
        categoria.setUpdatedAt(LocalDateTime.now());

        categoria = categoriaRepository.save(categoria);
        return CategoriaResponseDTO.fromEntity(categoria);
    }


    public List<CategoriaResponseDTO> buscarTodos() {
        return categoriaRepository.findAll().stream()
                .map(CategoriaResponseDTO::fromEntity)
                .toList();
    }

    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
        return CategoriaResponseDTO.fromEntity(categoria);
    }


    public CategoriaResponseDTO atualizarCategoria(Long id, CategoriaRequestDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));

        verificarNomeDuplicadoAtualizar(categoriaDTO.getNome(), id);

        categoria.setNome(categoriaDTO.getNome());
        categoria.setDescricao(categoriaDTO.getDescricao());
        categoria.setUpdatedAt(LocalDateTime.now());

        categoria = categoriaRepository.save(categoria);
        return CategoriaResponseDTO.fromEntity(categoria);
    }


    private void verificarNomeDuplicado(String nome) {
        if (categoriaRepository.existsByNome(nome)) {
            throw new DuplicateResourceException("Nome", nome);
        }
    }

    private void verificarNomeDuplicadoAtualizar(String nome, Long id) {
        if (categoriaRepository.existsByNomeAndIdNot(nome, id)) {
            throw new DuplicateResourceException("Nome", nome);
        }
    }


    public void excluirCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
        categoriaRepository.delete(categoria);
    }
}
