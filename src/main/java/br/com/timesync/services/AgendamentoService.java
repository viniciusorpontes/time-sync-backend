package br.com.timesync.services;

import br.com.timesync.dto.AlterarAgendamentoDTO;
import br.com.timesync.dto.BuscarAgendamentoDTO;
import br.com.timesync.dto.SalvarOuAlterarAgendamentoDTO;
import br.com.timesync.entities.Agendamento;
import br.com.timesync.entities.Empresa;
import br.com.timesync.entities.Servico;
import br.com.timesync.entities.Usuario;
import br.com.timesync.exceptions.ObjectNotFoundException;
import br.com.timesync.repositories.AgendamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ServicoService servicoService;
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;

    public Agendamento buscarPorId(Integer id) {
        return agendamentoRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Id %s não encontrado", id)));
    }

    public List<BuscarAgendamentoDTO> buscarAgendamentosPorEmpresaId(Long empresaId) {
        final List<Agendamento> agendamentos = agendamentoRepository.findAllByEmpresaId(empresaId);
        return agendamentos.stream().map(BuscarAgendamentoDTO::toDTO).toList();
    }

    public Agendamento salvar(SalvarOuAlterarAgendamentoDTO salvarAgendamentoDTO) {
        final LocalDateTime dataChegada = salvarAgendamentoDTO.dataChegada();
        final List<Servico> servicos = getServicos(salvarAgendamentoDTO.idsServicos());
        final Usuario cliente = usuarioService.buscarPorId(salvarAgendamentoDTO.clienteId());
        final Usuario consumidor = usuarioService.buscarPorId(salvarAgendamentoDTO.consumidorId());
        final Empresa empresa = empresaService.buscarEmpresaPorId(salvarAgendamentoDTO.empresaId());
        final Agendamento agendamento = new Agendamento(empresa, dataChegada, getDataSaida(dataChegada, servicos), servicos, cliente, consumidor);
        return this.agendamentoRepository.save(agendamento);
    }

    public Agendamento alterar(Integer id, SalvarOuAlterarAgendamentoDTO alterarEmpresaDTO) {
        final Agendamento agendamento = buscarPorId(id);
        agendamento.setDataChegada(alterarEmpresaDTO.dataChegada());
        agendamento.setServicos(getServicos(alterarEmpresaDTO.idsServicos()));
        agendamento.setDataSaida(getDataSaida(agendamento.getDataChegada(), agendamento.getServicos()));
        agendamento.setCliente(usuarioService.buscarPorId(alterarEmpresaDTO.clienteId()));
        agendamento.setConsumidor(usuarioService.buscarPorId(alterarEmpresaDTO.consumidorId()));
        agendamento.setEmpresa(empresaService.buscarEmpresaPorId(alterarEmpresaDTO.empresaId()));
        return this.agendamentoRepository.save(agendamento);
    }

    public Agendamento alterarDataAgendamento(Integer id, AlterarAgendamentoDTO agendamentoDTO) {

        final Usuario usuario = usuarioService.buscarPorId(agendamentoDTO.usuarioId());

        final Agendamento agendamento = buscarPorId(id);
        agendamento.setDataChegada(agendamentoDTO.dataChegada());
        agendamento.setDataSaida(getDataSaida(agendamento.getDataChegada(), agendamento.getServicos()));
        agendamento.setCliente(usuario);
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

    public Map<String, Long> buscarQuantidadeDeAgendamentosPorMes(Long empresaId, Long funcionarioId) {

        List<Object[]> results;

        if (funcionarioId != null && funcionarioId != 0) {
            results = agendamentoRepository.findAgendamentosCountByMonthAndEmpresaIdAndFuncionarioId(empresaId, funcionarioId);
        } else {
            results = agendamentoRepository.findAgendamentosCountByMonthAndEmpresaId(empresaId);
        }

        final Map<String, Long> agendamentosPorMes = new HashMap<>();
        for (Object[] row : results) {
            final String mes = (String) row[0];
            final Long totalAgendamentos = (Long) row[1];
            agendamentosPorMes.put(mes, totalAgendamentos);
        }

        return agendamentosPorMes;
    }

}