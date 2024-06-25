package br.com.timesync.controllers;

import br.com.timesync.dto.AgendamentoDTO;
import br.com.timesync.entities.Agendamento;
import br.com.timesync.entities.Servico;
import br.com.timesync.entities.Usuario;
import br.com.timesync.enums.UsuarioEnum;
import br.com.timesync.services.AgendamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AgendamentoControllerTest {

    @InjectMocks
    private AgendamentoController agendamentoController;

    @Mock
    private AgendamentoService agendamentoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    @DisplayName("Teste busca de agendamento por id com sucesso")
    public void testeBuscarPorIdComSucesso() {

        final Agendamento agendamento = getAgendamento();

        when(agendamentoService.buscarPorId(1)).thenReturn(agendamento);

        ResponseEntity<Agendamento> response = agendamentoController.buscarPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agendamento, response.getBody());
    }

    @Test
    @DisplayName("Teste salvar agendamento com sucesso")
    public void testSalvarComSucesso() {
        final AgendamentoDTO agendamentoDTO = mock(AgendamentoDTO.class);

        final Agendamento agendamento = new Agendamento();
        agendamento.setId(1);

        when(agendamentoService.salvar(agendamentoDTO)).thenReturn(agendamento);

        ResponseEntity<Agendamento> response = agendamentoController.salvar(agendamentoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(agendamento, response.getBody());
    }

    private static Agendamento getAgendamento() {
        final Servico servico = getServico();
        final Usuario cliente = getCliente();
        final Usuario consumidor = getConsumidor();

        final LocalDateTime dataAtual = LocalDateTime.now();

        return new Agendamento(1,
                dataAtual,
                dataAtual.plusHours(1),
                Boolean.TRUE,
                Collections.singletonList(servico),
                cliente,
                consumidor
        );
    }

    private static Servico getServico() {
        return new Servico(1,
                "Corte de Cabelo",
                LocalTime.of(1, 0),
                new BigDecimal("35"),
                Boolean.TRUE,
                getCliente().getId()
        );
    }

    private static Usuario getCliente() {
        return new Usuario(1,
                "123.456.789-10",
                "Vinicius",
                "vinicius@email.com",
                "14998124578",
                "123456",
                UsuarioEnum.CLIENTE,
                Boolean.TRUE
        );
    }

    private static Usuario getConsumidor() {
        return new Usuario(2,
                "123.456.789-11",
                "Matheus",
                "matheus@email.com",
                "14998124579",
                "123456",
                UsuarioEnum.CONSUMIDOR,
                Boolean.TRUE
        );
    }

}
