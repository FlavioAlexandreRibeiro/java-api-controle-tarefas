package com.API_Controle_Tarefas.controller;

import com.API_Controle_Tarefas.dto.UsuarioRequestDTO;
import com.API_Controle_Tarefas.dto.UsuarioResponseDTO;
import com.API_Controle_Tarefas.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private UsuarioResponseDTO usuarioResponseDTO;
    private UsuarioRequestDTO usuarioRequestDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
        objectMapper = new ObjectMapper();

        usuarioResponseDTO = new UsuarioResponseDTO(
                1L,
                "João Silva",
                "joao@email.com",
                "11999999999",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setNome("João Silva");
        usuarioRequestDTO.setEmail("joao@email.com");
        usuarioRequestDTO.setSenha("senha123");
        usuarioRequestDTO.setTelefone("11999999999");
    }

    @Test
    void cadastrarUsuario_Sucesso() throws Exception {
        when(usuarioService.cadastrarUsuario(any(UsuarioRequestDTO.class))).thenReturn(usuarioResponseDTO);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.telefone").value("11999999999"));
    }

    @Test
    void buscarTodos_Sucesso() throws Exception {
        UsuarioResponseDTO usuario2 = new UsuarioResponseDTO(
                2L,
                "Maria Santos",
                "maria@email.com",
                "11888888888",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        List<UsuarioResponseDTO> usuarios = Arrays.asList(usuarioResponseDTO, usuario2);
        when(usuarioService.buscarTodos()).thenReturn(usuarios);

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("João Silva"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nome").value("Maria Santos"));
    }

    @Test
    void buscarPorId_Sucesso() throws Exception {
        when(usuarioService.buscarPorId(anyLong())).thenReturn(usuarioResponseDTO);

        mockMvc.perform(get("/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    void atualizarUsuario_Sucesso() throws Exception {
        when(usuarioService.atualizarUsuario(anyLong(), any(UsuarioRequestDTO.class))).thenReturn(usuarioResponseDTO);

        mockMvc.perform(put("/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    void excluirUsuario_Sucesso() throws Exception {
        mockMvc.perform(delete("/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}
