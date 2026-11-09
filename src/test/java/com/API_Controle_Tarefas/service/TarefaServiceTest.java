package com.API_Controle_Tarefas.service;

import com.API_Controle_Tarefas.dto.TarefaRequestDTO;
import com.API_Controle_Tarefas.dto.TarefaResponseDTO;
import com.API_Controle_Tarefas.entity.*;
import com.API_Controle_Tarefas.exception.ResourceNotFoundException;
import com.API_Controle_Tarefas.repository.CategoriaRepository;
import com.API_Controle_Tarefas.repository.TarefaRepository;
import com.API_Controle_Tarefas.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private TarefaService tarefaService;

    private Tarefa tarefa;
    private Usuario usuario;
    private Categoria categoria;
    private TarefaRequestDTO tarefaDTO;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("João Silva");
        usuario.setEmail("joao@email.com");
        usuario.setSenha("senha123");
        usuario.setTelefone("11999999999");
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());

        categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Trabalho");
        categoria.setDescricao("Tarefas de trabalho");
        categoria.setCreatedAt(LocalDateTime.now());
        categoria.setUpdatedAt(LocalDateTime.now());

        tarefa = new Tarefa();
        tarefa.setId(1L);
        tarefa.setTitulo("Projeto API");
        tarefa.setDescricao("Desenvolver API de tarefas");
        tarefa.setStatus(Status.PENDENTE);
        tarefa.setPrioridade(Prioridade.ALTA);
        tarefa.setDataVencimento(LocalDate.of(2024, 12, 31));
        tarefa.setUsuario(usuario);
        tarefa.setCategoria(categoria);
        tarefa.setCreatedAt(LocalDateTime.now());
        tarefa.setUpdatedAt(LocalDateTime.now());

        tarefaDTO = new TarefaRequestDTO();
        tarefaDTO.setTitulo("Projeto API");
        tarefaDTO.setDescricao("Desenvolver API de tarefas");
        tarefaDTO.setStatus(Status.PENDENTE);
        tarefaDTO.setPrioridade(Prioridade.ALTA);
        tarefaDTO.setDataVencimento(LocalDate.of(2024, 12, 31));
        tarefaDTO.setUsuarioId(1L);
        tarefaDTO.setCategoriaId(1L);
    }

    @Test
    void cadastrarTarefa_Sucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        TarefaResponseDTO result = tarefaService.cadastrarTarefa(tarefaDTO);

        assertNotNull(result);
        assertEquals("Projeto API", result.titulo());
        assertEquals("Desenvolver API de tarefas", result.descricao());
        assertEquals(Status.PENDENTE, result.status());
        assertEquals(Prioridade.ALTA, result.prioridade());
        verify(usuarioRepository, times(1)).findById(1L);
        verify(categoriaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void cadastrarTarefa_UsuarioNaoEncontrado_LancaExcecao() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tarefaService.cadastrarTarefa(tarefaDTO));
        verify(usuarioRepository, times(1)).findById(1L);
        verify(categoriaRepository, never()).findById(anyLong());
        verify(tarefaRepository, never()).save(any(Tarefa.class));
    }

    @Test
    void cadastrarTarefa_CategoriaNaoEncontrada_LancaExcecao() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tarefaService.cadastrarTarefa(tarefaDTO));
        verify(usuarioRepository, times(1)).findById(1L);
        verify(categoriaRepository, times(1)).findById(1L);
        verify(tarefaRepository, never()).save(any(Tarefa.class));
    }

    @Test
    void buscarPorId_Sucesso() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));

        TarefaResponseDTO result = tarefaService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Projeto API", result.titulo());
        verify(tarefaRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_NaoEncontrado_LancaExcecao() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tarefaService.buscarPorId(1L));
        verify(tarefaRepository, times(1)).findById(1L);
    }

    @Test
    void buscasTodos_Sucesso() {
        Tarefa tarefa2 = new Tarefa();
        tarefa2.setId(2L);
        tarefa2.setTitulo("Projeto Frontend");
        tarefa2.setDescricao("Desenvolver frontend");
        tarefa2.setStatus(Status.EM_ANDAMENTO);
        tarefa2.setPrioridade(Prioridade.MEDIA);
        tarefa2.setDataVencimento(LocalDate.of(2024, 11, 30));
        tarefa2.setUsuario(usuario);
        tarefa2.setCategoria(categoria);
        tarefa2.setCreatedAt(LocalDateTime.now());
        tarefa2.setUpdatedAt(LocalDateTime.now());

        Pageable pageable = PageRequest.of(0, 10);
        Page<Tarefa> page = new PageImpl<>(Arrays.asList(tarefa, tarefa2), pageable, 2);

        when(tarefaRepository.findAll(any(org.springframework.data.jpa.domain.Specification.class), any(Pageable.class))).thenReturn(page);

        Page<TarefaResponseDTO> result = tarefaService.buscasTodos(null, null, null, null, null, null, null, pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Projeto API", result.getContent().get(0).titulo());
        assertEquals("Projeto Frontend", result.getContent().get(1).titulo());
        verify(tarefaRepository, times(1)).findAll(any(org.springframework.data.jpa.domain.Specification.class), any(Pageable.class));
    }

    @Test
    void atualizarTarefa_Sucesso() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);

        TarefaRequestDTO dtoAtualizado = new TarefaRequestDTO();
        dtoAtualizado.setTitulo("Projeto API Atualizado");
        dtoAtualizado.setDescricao("API atualizada");
        dtoAtualizado.setStatus(Status.CONCLUIDA);
        dtoAtualizado.setPrioridade(Prioridade.BAIXA);
        dtoAtualizado.setDataVencimento(LocalDate.of(2025, 1, 15));
        dtoAtualizado.setUsuarioId(1L);
        dtoAtualizado.setCategoriaId(1L);

        TarefaResponseDTO result = tarefaService.atualizarTarefa(1L, dtoAtualizado);

        assertNotNull(result);
        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void atualizarTarefa_NaoEncontrado_LancaExcecao() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tarefaService.atualizarTarefa(1L, tarefaDTO));
        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, never()).save(any(Tarefa.class));
    }

    @Test
    void excluirTarefa_Sucesso() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.of(tarefa));
        doNothing().when(tarefaRepository).delete(any(Tarefa.class));

        tarefaService.excluirTarefa(1L);

        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, times(1)).delete(tarefa);
    }

    @Test
    void excluirTarefa_NaoEncontrado_LancaExcecao() {
        when(tarefaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tarefaService.excluirTarefa(1L));
        verify(tarefaRepository, times(1)).findById(1L);
        verify(tarefaRepository, never()).delete(any(Tarefa.class));
    }
}
