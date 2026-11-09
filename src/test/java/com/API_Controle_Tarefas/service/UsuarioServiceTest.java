package com.API_Controle_Tarefas.service;

import com.API_Controle_Tarefas.dto.UsuarioRequestDTO;
import com.API_Controle_Tarefas.dto.UsuarioResponseDTO;
import com.API_Controle_Tarefas.entity.Usuario;
import com.API_Controle_Tarefas.exception.DuplicateResourceException;
import com.API_Controle_Tarefas.exception.ResourceNotFoundException;
import com.API_Controle_Tarefas.repository.UsuarioRepository;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;
    private UsuarioRequestDTO usuarioDTO;

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

        usuarioDTO = new UsuarioRequestDTO();
        usuarioDTO.setNome("João Silva");
        usuarioDTO.setEmail("joao@email.com");
        usuarioDTO.setSenha("senha123");
        usuarioDTO.setTelefone("11999999999");
    }

    @Test
    void cadastrarUsuario_Sucesso() {
        when(usuarioRepository.existsByNome(anyString())).thenReturn(false);
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioResponseDTO result = usuarioService.cadastrarUsuario(usuarioDTO);

        assertNotNull(result);
        assertEquals("João Silva", result.nome());
        assertEquals("joao@email.com", result.email());
        assertEquals("11999999999", result.telefone());
        verify(usuarioRepository, times(1)).existsByNome("João Silva");
        verify(usuarioRepository, times(1)).existsByEmail("joao@email.com");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void cadastrarUsuario_NomeDuplicado_LancaExcecao() {
        when(usuarioRepository.existsByNome(anyString())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> usuarioService.cadastrarUsuario(usuarioDTO));
        verify(usuarioRepository, times(1)).existsByNome("João Silva");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void cadastrarUsuario_EmailDuplicado_LancaExcecao() {
        when(usuarioRepository.existsByNome(anyString())).thenReturn(false);
        when(usuarioRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> usuarioService.cadastrarUsuario(usuarioDTO));
        verify(usuarioRepository, times(1)).existsByNome("João Silva");
        verify(usuarioRepository, times(1)).existsByEmail("joao@email.com");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void buscarTodos_Sucesso() {
        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNome("Maria Santos");
        usuario2.setEmail("maria@email.com");
        usuario2.setSenha("senha456");
        usuario2.setTelefone("11888888888");
        usuario2.setCreatedAt(LocalDateTime.now());
        usuario2.setUpdatedAt(LocalDateTime.now());

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario, usuario2));

        List<UsuarioResponseDTO> result = usuarioService.buscarTodos();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("João Silva", result.get(0).nome());
        assertEquals("Maria Santos", result.get(1).nome());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_Sucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO result = usuarioService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("João Silva", result.nome());
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_NaoEncontrado_LancaExcecao() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.buscarPorId(1L));
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void atualizarUsuario_Sucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByNomeAndIdNot(anyString(), any(Long.class))).thenReturn(false);
        when(usuarioRepository.existsByEmailAndIdNot(anyString(), any(Long.class))).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        UsuarioRequestDTO dtoAtualizado = new UsuarioRequestDTO();
        dtoAtualizado.setNome("João Silva Atualizado");
        dtoAtualizado.setEmail("joao.novo@email.com");
        dtoAtualizado.setSenha("novasenha123");
        dtoAtualizado.setTelefone("11777777777");

        UsuarioResponseDTO result = usuarioService.atualizarUsuario(1L, dtoAtualizado);

        assertNotNull(result);
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).existsByNomeAndIdNot("João Silva Atualizado", 1L);
        verify(usuarioRepository, times(1)).existsByEmailAndIdNot("joao.novo@email.com", 1L);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void atualizarUsuario_NomeDuplicado_LancaExcecao() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByNomeAndIdNot(anyString(), any(Long.class))).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> usuarioService.atualizarUsuario(1L, usuarioDTO));
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).existsByNomeAndIdNot("João Silva", 1L);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void atualizarUsuario_EmailDuplicado_LancaExcecao() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.existsByNomeAndIdNot(anyString(), any(Long.class))).thenReturn(false);
        when(usuarioRepository.existsByEmailAndIdNot(anyString(), any(Long.class))).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> usuarioService.atualizarUsuario(1L, usuarioDTO));
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).existsByEmailAndIdNot("joao@email.com", 1L);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void atualizarUsuario_NaoEncontrado_LancaExcecao() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.atualizarUsuario(1L, usuarioDTO));
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    @Test
    void excluirUsuario_Sucesso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepository).delete(any(Usuario.class));

        usuarioService.excluirUsuario(1L);

        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    void excluirUsuario_NaoEncontrado_LancaExcecao() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.excluirUsuario(1L));
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, never()).delete(any(Usuario.class));
    }
}
