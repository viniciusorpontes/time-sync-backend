package br.com.timesync.services;

import br.com.timesync.dto.AgendamentoDTO;
import br.com.timesync.entities.Agendamento;
import br.com.timesync.entities.Servico;
import br.com.timesync.entities.Usuario;
import br.com.timesync.enums.UsuarioEnum;
import br.com.timesync.exceptions.ObjectNotFoundException;
import br.com.timesync.repositories.AgendamentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @Mock
    private ServicoService servicoService;

    @Mock
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Teste busca de agendamento por id com sucesso")
    public void testeBuscarPorIdComSucesso() {
        final Agendamento agendamento = getAgendamento();

        when(agendamentoRepository.findById(1)).thenReturn(Optional.of(agendamento));

        final Agendamento resultadoAgendamento = agendamentoService.buscarPorId(1);

        assertNotNull(resultadoAgendamento);
        assertEquals(agendamento, resultadoAgendamento);
    }

    @Test
    @DisplayName("Teste busca de agendamento não encontrado")
    public void testeBuscarPorIdComErroObjectNotFound() {
        when(agendamentoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> agendamentoService.buscarPorId(1));
    }

    @Test
    @DisplayName("Teste salvar agendamento com sucesso")
    public void testSalvarComSucesso() {
        final AgendamentoDTO agendamentoDTO = new AgendamentoDTO(
                LocalDateTime.now(),
                Collections.singletonList(1),
                1,
                2
        );

        final Usuario cliente = getCliente();
        final Usuario consumidor = getConsumidor();
        final Servico servico = getServico();

        when(usuarioService.buscarPorId(agendamentoDTO.clienteId())).thenReturn(cliente);
        when(usuarioService.buscarPorId(agendamentoDTO.consumidorId())).thenReturn(consumidor);
        when(servicoService.buscarPorId(anyInt())).thenReturn(servico);

        final Agendamento resultadoAgendamento = agendamentoService.salvar(agendamentoDTO);

        assertNotNull(resultadoAgendamento);
        assertEquals(cliente, resultadoAgendamento.getCliente());
        assertEquals(consumidor, resultadoAgendamento.getConsumidor());
        assertFalse(resultadoAgendamento.getServicos().isEmpty());
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
