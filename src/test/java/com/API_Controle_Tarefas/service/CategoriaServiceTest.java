package com.API_Controle_Tarefas.service;

import com.API_Controle_Tarefas.dto.CategoriaRequestDTO;
import com.API_Controle_Tarefas.dto.CategoriaResponseDTO;
import com.API_Controle_Tarefas.entity.Categoria;
import com.API_Controle_Tarefas.exception.DuplicateResourceException;
import com.API_Controle_Tarefas.exception.ResourceNotFoundException;
import com.API_Controle_Tarefas.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private Categoria categoria;
    private CategoriaRequestDTO categoriaDTO;

    @BeforeEach
    void setUp() {
        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Trabalho");
        categoria.setDescricao("Tarefas de trabalho");
        categoria.setCreatedAt(LocalDateTime.now());
        categoria.setUpdatedAt(LocalDateTime.now());

        categoriaDTO = new CategoriaRequestDTO();
        categoriaDTO.setNome("Trabalho");
        categoriaDTO.setDescricao("Tarefas de trabalho");
    }

    @Test
    void cadastrarCategoria_Sucesso() {
        when(categoriaRepository.existsByNome(anyString())).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        CategoriaResponseDTO result = categoriaService.cadastrarCategoria(categoriaDTO);

        assertNotNull(result);
        assertEquals("Trabalho", result.nome());
        assertEquals("Tarefas de trabalho", result.descricao());
        verify(categoriaRepository, times(1)).existsByNome("Trabalho");
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void cadastrarCategoria_NomeDuplicado_LancaExcecao() {
        when(categoriaRepository.existsByNome(anyString())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> categoriaService.cadastrarCategoria(categoriaDTO));
        verify(categoriaRepository, times(1)).existsByNome("Trabalho");
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void buscarTodos_Sucesso() {
        Categoria categoria2 = new Categoria();
        categoria2.setId(2L);
        categoria2.setNome("Pessoal");
        categoria2.setDescricao("Tarefas pessoais");
        categoria2.setCreatedAt(LocalDateTime.now());
        categoria2.setUpdatedAt(LocalDateTime.now());

        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(categoria, categoria2));

        List<CategoriaResponseDTO> result = categoriaService.buscarTodos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Trabalho", result.get(0).nome());
        assertEquals("Pessoal", result.get(1).nome());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_Sucesso() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        CategoriaResponseDTO result = categoriaService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Trabalho", result.nome());
        verify(categoriaRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_NaoEncontrado_LancaExcecao() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoriaService.buscarPorId(1L));
        verify(categoriaRepository, times(1)).findById(1L);
    }

    @Test
    void atualizarCategoria_Sucesso() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.existsByNomeAndIdNot(anyString(), any(Long.class))).thenReturn(false);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        CategoriaRequestDTO dtoAtualizado = new CategoriaRequestDTO();
        dtoAtualizado.setNome("Trabalho Atualizado");
        dtoAtualizado.setDescricao("Tarefas de trabalho atualizadas");

        CategoriaResponseDTO result = categoriaService.atualizarCategoria(1L, dtoAtualizado);

        assertNotNull(result);
        verify(categoriaRepository, times(1)).findById(1L);
        verify(categoriaRepository, times(1)).existsByNomeAndIdNot("Trabalho Atualizado", 1L);
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    void atualizarCategoria_NomeDuplicado_LancaExcecao() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.existsByNomeAndIdNot(anyString(), any(Long.class))).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> categoriaService.atualizarCategoria(1L, categoriaDTO));
        verify(categoriaRepository, times(1)).findById(1L);
        verify(categoriaRepository, times(1)).existsByNomeAndIdNot("Trabalho", 1L);
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void atualizarCategoria_NaoEncontrado_LancaExcecao() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoriaService.atualizarCategoria(1L, categoriaDTO));
        verify(categoriaRepository, times(1)).findById(1L);
        verify(categoriaRepository, never()).save(any(Categoria.class));
    }

    @Test
    void excluirCategoria_Sucesso() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        doNothing().when(categoriaRepository).delete(any(Categoria.class));

        categoriaService.excluirCategoria(1L);

        verify(categoriaRepository, times(1)).findById(1L);
        verify(categoriaRepository, times(1)).delete(categoria);
    }

    @Test
    void excluirCategoria_NaoEncontrado_LancaExcecao() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoriaService.excluirCategoria(1L));
        verify(categoriaRepository, times(1)).findById(1L);
        verify(categoriaRepository, never()).delete(any(Categoria.class));
    }
}
