package br.com.timesync.controllers;

import br.com.timesync.dto.BuscarAgendamentoDTO;
import br.com.timesync.dto.SalvarOuAlterarAgendamentoDTO;
import br.com.timesync.entities.Agendamento;
import br.com.timesync.entities.Empresa;
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
        final BuscarAgendamentoDTO agendamentoDTO = getBuscarAgendamentoDTO(agendamento);

        when(agendamentoService.buscarPorId(1)).thenReturn(agendamento);

        ResponseEntity<BuscarAgendamentoDTO> response = agendamentoController.buscarPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(agendamentoDTO, response.getBody());
    }

    @Test
    @DisplayName("Teste salvar agendamento com sucesso")
    public void testSalvarComSucesso() {
        final SalvarOuAlterarAgendamentoDTO salvarOuAlterarAgendamentoDTO = mock(SalvarOuAlterarAgendamentoDTO.class);

        final Agendamento agendamento = getAgendamento();

        final BuscarAgendamentoDTO agendamentoDTO = getBuscarAgendamentoDTO(agendamento);

        when(agendamentoService.salvar(salvarOuAlterarAgendamentoDTO)).thenReturn(agendamento);

        ResponseEntity<BuscarAgendamentoDTO> response = agendamentoController.salvar(salvarOuAlterarAgendamentoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(agendamentoDTO, response.getBody());
    }

    private static Agendamento getAgendamento() {
        final Servico servico = getServico();
        final Usuario cliente = getCliente();
        final Usuario consumidor = getConsumidor();
        final Empresa empresa  = getEmpresa();

        final LocalDateTime dataAtual = LocalDateTime.now();

        return new Agendamento(1,
                dataAtual,
                dataAtual.plusHours(1),
                Boolean.TRUE,
                Collections.singletonList(servico),
                cliente,
                consumidor,
                empresa
        );
    }

    private BuscarAgendamentoDTO getBuscarAgendamentoDTO(Agendamento agendamento) {
        return BuscarAgendamentoDTO.toDTO(agendamento);
    }

    private static Empresa getEmpresa() {
        final Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNome("Barbearia do Vinicius");
        return empresa;
    }

    private static Servico getServico() {
        return new Servico(1,
                "Corte de Cabelo",
                LocalTime.of(1, 0),
                new BigDecimal("35"),
                Collections.singletonList(getCliente()),
                Boolean.TRUE,
                getEmpresa()
        );
    }

    private static Usuario getCliente() {
        final Usuario cliente = new Usuario();
        cliente.setId(1L);
        cliente.setCpf("123.456.789-10");
        cliente.setNome("Vinicius");
        cliente.setEmail("vinicius@email.com");
        cliente.setTelefone("14998124578");
        cliente.setTipo(UsuarioEnum.CLIENTE);
        cliente.setSenha("123456");
        cliente.setAtivo(Boolean.TRUE);
        return cliente;
    }

    private static Usuario getConsumidor() {
        final Usuario consumidor = new Usuario();
        consumidor.setId(2L);
        consumidor.setCpf("Matheus");
        consumidor.setNome("Vinicius");
        consumidor.setEmail("matheus@email.com");
        consumidor.setTelefone("14998124579");
        consumidor.setTipo(UsuarioEnum.CONSUMIDOR);
        consumidor.setSenha("123456");
        consumidor.setAtivo(Boolean.TRUE);
        return consumidor;
    }

}
