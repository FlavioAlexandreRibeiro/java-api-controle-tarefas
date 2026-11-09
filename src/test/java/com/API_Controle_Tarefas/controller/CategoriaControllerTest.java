package com.API_Controle_Tarefas.controller;

import com.API_Controle_Tarefas.dto.CategoriaRequestDTO;
import com.API_Controle_Tarefas.dto.CategoriaResponseDTO;
import com.API_Controle_Tarefas.service.CategoriaService;
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
class CategoriaControllerTest {

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private CategoriaController categoriaController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private CategoriaResponseDTO categoriaResponseDTO;
    private CategoriaRequestDTO categoriaRequestDTO;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoriaController).build();
        objectMapper = new ObjectMapper();

        categoriaResponseDTO = new CategoriaResponseDTO(
                1L,
                "Trabalho",
                "Tarefas de trabalho",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        categoriaRequestDTO = new CategoriaRequestDTO();
        categoriaRequestDTO.setNome("Trabalho");
        categoriaRequestDTO.setDescricao("Tarefas de trabalho");
    }

    @Test
    void cadastrarCategoria_Sucesso() throws Exception {
        when(categoriaService.cadastrarCategoria(any(CategoriaRequestDTO.class))).thenReturn(categoriaResponseDTO);

        mockMvc.perform(post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Trabalho"))
                .andExpect(jsonPath("$.descricao").value("Tarefas de trabalho"));
    }

    @Test
    void buscarTodos_Sucesso() throws Exception {
        CategoriaResponseDTO categoria2 = new CategoriaResponseDTO(
                2L,
                "Pessoal",
                "Tarefas pessoais",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        List<CategoriaResponseDTO> categorias = Arrays.asList(categoriaResponseDTO, categoria2);
        when(categoriaService.buscarTodos()).thenReturn(categorias);

        mockMvc.perform(get("/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].nome").value("Trabalho"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].nome").value("Pessoal"));
    }

    @Test
    void buscarPorId_Sucesso() throws Exception {
        when(categoriaService.buscarPorId(anyLong())).thenReturn(categoriaResponseDTO);

        mockMvc.perform(get("/categorias/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Trabalho"))
                .andExpect(jsonPath("$.descricao").value("Tarefas de trabalho"));
    }

    @Test
    void atualizarCategoria_Sucesso() throws Exception {
        when(categoriaService.atualizarCategoria(anyLong(), any(CategoriaRequestDTO.class))).thenReturn(categoriaResponseDTO);

        mockMvc.perform(put("/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoriaRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Trabalho"))
                .andExpect(jsonPath("$.descricao").value("Tarefas de trabalho"));
    }

    @Test
    void excluirCategoria_Sucesso() throws Exception {
        mockMvc.perform(delete("/categorias/1"))
                .andExpect(status().isNoContent());
    }
}
