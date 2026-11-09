package com.API_Controle_Tarefas.service;

import com.API_Controle_Tarefas.dto.UsuarioRequestDTO;
import com.API_Controle_Tarefas.dto.UsuarioResponseDTO;
import com.API_Controle_Tarefas.entity.Usuario;
import com.API_Controle_Tarefas.exception.DuplicateResourceException;
import com.API_Controle_Tarefas.exception.ResourceNotFoundException;
import com.API_Controle_Tarefas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class    UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponseDTO cadastrarUsuario(UsuarioRequestDTO usuarioDTO) {

        verificarNomeDuplicado(usuarioDTO.getNome());
        verificarEmailDuplicado(usuarioDTO.getEmail());

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());

        usuario = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.fromEntity(usuario);
    }

    public List<UsuarioResponseDTO> buscarTodos() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponseDTO::fromEntity)
                .toList();
    }

    public UsuarioResponseDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        return UsuarioResponseDTO.fromEntity(usuario);
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO usuarioDTO) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));

        verificarNomeDuplicadoAtualizar(usuarioDTO.getNome(), id);
        verificarEmailDuplicadoAtualizar(usuarioDTO.getEmail(), id);

        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setTelefone(usuarioDTO.getTelefone());
        usuario.setUpdatedAt(LocalDateTime.now());

        usuario = usuarioRepository.save(usuario);
        return UsuarioResponseDTO.fromEntity(usuario);
    }

    private void verificarNomeDuplicado(String nome) {
        if (usuarioRepository.existsByNome(nome)) {
            throw new DuplicateResourceException("Nome", nome);
        }
    }

    private void verificarEmailDuplicado(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("Email", email);
        }
    }

    private void verificarNomeDuplicadoAtualizar(String nome, Long id) {
        if (usuarioRepository.existsByNomeAndIdNot(nome, id)) {
            throw new DuplicateResourceException("Nome", nome);
        }
    }

    private void verificarEmailDuplicadoAtualizar(String email, Long id) {
        if (usuarioRepository.existsByEmailAndIdNot(email, id)) {
            throw new DuplicateResourceException("Email", email);
        }
    }

    public void excluirUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", id));
        usuarioRepository.delete(usuario);
    }
}
