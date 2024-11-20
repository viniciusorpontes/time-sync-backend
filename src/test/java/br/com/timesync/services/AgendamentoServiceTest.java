package br.com.timesync.services;

import br.com.timesync.dto.SalvarOuAlterarAgendamentoDTO;
import br.com.timesync.entities.Agendamento;
import br.com.timesync.entities.Empresa;
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
import static org.mockito.Mockito.*;

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

    @Mock
    private EmpresaService empresaService;

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
        final SalvarOuAlterarAgendamentoDTO salvarOuAlterarAgendamentoDTO = new SalvarOuAlterarAgendamentoDTO(
                1L,
                LocalDateTime.now(),
                Collections.singletonList(1),
                1L,
                2L
        );

        final Usuario cliente = getCliente();
        final Usuario consumidor = getConsumidor();
        final Servico servico = getServico();
        final Empresa empresa = getEmpresa();

        when(usuarioService.buscarPorId(salvarOuAlterarAgendamentoDTO.clienteId())).thenReturn(cliente);
        when(usuarioService.buscarPorId(salvarOuAlterarAgendamentoDTO.consumidorId())).thenReturn(consumidor);
        when(servicoService.buscarPorId(anyInt())).thenReturn(servico);
        when(empresaService.buscarEmpresaPorId(anyLong())).thenReturn(empresa);
        when(agendamentoRepository.save(any())).thenReturn(getAgendamento());

        final Agendamento resultadoAgendamento = agendamentoService.salvar(salvarOuAlterarAgendamentoDTO);

        assertNotNull(resultadoAgendamento);
        assertEquals(cliente, resultadoAgendamento.getCliente());
        assertEquals(consumidor, resultadoAgendamento.getConsumidor());
        assertFalse(resultadoAgendamento.getServicos().isEmpty());
    }

    private static Agendamento getAgendamento() {
        final Servico servico = getServico();
        final Usuario cliente = getCliente();
        final Usuario consumidor = getConsumidor();
        final Empresa empresa = getEmpresa();

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

    private static Empresa getEmpresa() {
        final Empresa empresa = new Empresa();
        empresa.setId(1L);
        empresa.setNome("Barbearia do Vinicius");
        return empresa;
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
