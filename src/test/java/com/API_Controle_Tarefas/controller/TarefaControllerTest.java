package com.API_Controle_Tarefas.controller;

import com.API_Controle_Tarefas.dto.TarefaRequestDTO;
import com.API_Controle_Tarefas.dto.TarefaResponseDTO;
import com.API_Controle_Tarefas.entity.Prioridade;
import com.API_Controle_Tarefas.entity.Status;
import com.API_Controle_Tarefas.service.TarefaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TarefaControllerTest {

    @Mock
    private TarefaService tarefaService;

    @InjectMocks
    private TarefaController tarefaController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private TarefaResponseDTO tarefaResponseDTO;
    private TarefaRequestDTO tarefaRequestDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        mockMvc = MockMvcBuilders.standaloneSetup(tarefaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        tarefaResponseDTO = new TarefaResponseDTO(
                1L,
                "Projeto API",
                "Desenvolver API de tarefas",
                Status.PENDENTE,
                Prioridade.ALTA,
                LocalDate.of(2024, 12, 31),
                1L,
                1L,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        tarefaRequestDTO = new TarefaRequestDTO();
        tarefaRequestDTO.setTitulo("Projeto API");
        tarefaRequestDTO.setDescricao("Desenvolver API de tarefas");
        tarefaRequestDTO.setStatus(Status.PENDENTE);
        tarefaRequestDTO.setPrioridade(Prioridade.ALTA);
        tarefaRequestDTO.setDataVencimento(LocalDate.of(2024, 12, 31));
        tarefaRequestDTO.setUsuarioId(1L);
        tarefaRequestDTO.setCategoriaId(1L);
    }

    @Test
    void cadastrarTarefa_Sucesso() throws Exception {
        when(tarefaService.cadastrarTarefa(any(TarefaRequestDTO.class))).thenReturn(tarefaResponseDTO);

        mockMvc.perform(post("/tarefas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tarefaRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Projeto API"))
                .andExpect(jsonPath("$.descricao").value("Desenvolver API de tarefas"))
                .andExpect(jsonPath("$.status").value("PENDENTE"))
                .andExpect(jsonPath("$.prioridade").value("ALTA"));
    }

    @Test
    void buscarTodos_Sucesso() throws Exception {
        TarefaResponseDTO tarefa2 = new TarefaResponseDTO(
                2L,
                "Projeto Frontend",
                "Desenvolver frontend",
                Status.EM_ANDAMENTO,
                Prioridade.MEDIA,
                LocalDate.of(2024, 11, 30),
                1L,
                1L,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Pageable pageable = PageRequest.of(0, 10);
        Page<TarefaResponseDTO> page = new PageImpl<>(Arrays.asList(tarefaResponseDTO, tarefa2), pageable, 2);

        when(tarefaService.buscasTodos(any(), any(), any(), any(), any(), any(), any(), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/tarefas")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[0].titulo").value("Projeto API"))
                .andExpect(jsonPath("$.content[1].id").value(2L))
                .andExpect(jsonPath("$.content[1].titulo").value("Projeto Frontend"));
    }

    @Test
    void buscarPorId_Sucesso() throws Exception {
        when(tarefaService.buscarPorId(anyLong())).thenReturn(tarefaResponseDTO);

        mockMvc.perform(get("/tarefas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Projeto API"))
                .andExpect(jsonPath("$.descricao").value("Desenvolver API de tarefas"));
    }

    @Test
    void atualizarTarefa_Sucesso() throws Exception {
        when(tarefaService.atualizarTarefa(anyLong(), any(TarefaRequestDTO.class))).thenReturn(tarefaResponseDTO);

        mockMvc.perform(put("/tarefas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tarefaRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("Projeto API"))
                .andExpect(jsonPath("$.descricao").value("Desenvolver API de tarefas"));
    }

    @Test
    void excluirTarefa_Sucesso() throws Exception {
        mockMvc.perform(delete("/tarefas/1"))
                .andExpect(status().isNoContent());
    }
}
