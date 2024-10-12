package br.com.timesync.services;

import br.com.timesync.dto.BuscarAgendamentoDTO;
import br.com.timesync.dto.SalvarOuAlterarAgendamentoDTO;
import br.com.timesync.entities.Agendamento;
import br.com.timesync.entities.Servico;
import br.com.timesync.entities.Usuario;
import br.com.timesync.exceptions.ObjectNotFoundException;
import br.com.timesync.repositories.AgendamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ServicoService servicoService;
    private final UsuarioService usuarioService;

    public Agendamento buscarPorId(Integer id) {
        return agendamentoRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Id %s não encontrado", id)));
    }

    public List<BuscarAgendamentoDTO> buscarAgendamentosPorUsuariosIds(List<Integer> usuariosIds) {
        final List<Agendamento> agendamentos = agendamentoRepository.findAllByUsuariosIds(usuariosIds);
        return agendamentos.stream().map(BuscarAgendamentoDTO::toDTO).toList();
    }

    public Agendamento salvar(SalvarOuAlterarAgendamentoDTO salvarOuAlterarAgendamentoDTO) {
        final LocalDateTime dataChegada = salvarOuAlterarAgendamentoDTO.dataChegada();
        final List<Servico> servicos = getServicos(salvarOuAlterarAgendamentoDTO.idsServicos());
        final Usuario cliente = usuarioService.buscarPorId(salvarOuAlterarAgendamentoDTO.clienteId());
        final Usuario consumidor = usuarioService.buscarPorId(salvarOuAlterarAgendamentoDTO.consumidorId());
        final Agendamento agendamento = new Agendamento(dataChegada, getDataSaida(dataChegada, servicos), servicos, cliente, consumidor);
        return this.agendamentoRepository.save(agendamento);
    }

    public Agendamento alterar(Integer id, SalvarOuAlterarAgendamentoDTO salvarOuAlterarAgendamentoDTO) {
        final Agendamento agendamento = buscarPorId(id);
        agendamento.setDataChegada(salvarOuAlterarAgendamentoDTO.dataChegada());
        agendamento.setServicos(getServicos(salvarOuAlterarAgendamentoDTO.idsServicos()));
        agendamento.setDataSaida(getDataSaida(agendamento.getDataChegada(), agendamento.getServicos()));
        agendamento.setCliente(usuarioService.buscarPorId(salvarOuAlterarAgendamentoDTO.clienteId()));
        agendamento.setConsumidor(usuarioService.buscarPorId(salvarOuAlterarAgendamentoDTO.consumidorId()));
        return this.agendamentoRepository.save(agendamento);
    }

    public Agendamento alterarDataAgendamento(Integer id, LocalDateTime dataInicio) {
        final Agendamento agendamento = buscarPorId(id);
        agendamento.setDataChegada(dataInicio);
        agendamento.setDataSaida(getDataSaida(agendamento.getDataChegada(), agendamento.getServicos()));
        return this.agendamentoRepository.save(agendamento);
    }

    public void deletar(Integer id) {
        final Agendamento agendamento = buscarPorId(id);
        agendamento.setAtivo(false);
        this.agendamentoRepository.save(agendamento);
    }

    /** Função que recebe uma lista de ids de Servicos como parâmetro e retorna uma lista de Servicos */
    private List<Servico> getServicos(List<Integer> idsServicos) {
        return new ArrayList<>(idsServicos
                .stream()
                .map(servicoService::buscarPorId)
                .toList()
        );
    }

    private LocalDateTime getDataSaida(LocalDateTime dataChegada, List<Servico> servicos) {
        final Duration duracaoServicos = servicoService.calcularDuracaoServicos(servicos);
        return dataChegada.plusMinutes(duracaoServicos.toMinutes());
    }

}