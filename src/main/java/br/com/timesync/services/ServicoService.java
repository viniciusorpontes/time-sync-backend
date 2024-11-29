package br.com.timesync.services;

import br.com.timesync.dto.SalvarOuAlterarServicoDTO;
import br.com.timesync.entities.Empresa;
import br.com.timesync.entities.Servico;
import br.com.timesync.entities.Usuario;
import br.com.timesync.exceptions.ObjectNotFoundException;
import br.com.timesync.repositories.ServicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRespository;
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;

    public Servico buscarPorId(Integer id) {
        return this.servicoRespository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Id %s não encontrado", id)));
    }

    public Servico salvar(SalvarOuAlterarServicoDTO servicoDTO) {
        final List<Usuario> usuarios = usuarioService.buscarUsuariosPorIds(servicoDTO.idsUsuarios());
        final Empresa empresa = empresaService.buscarEmpresaPorId(servicoDTO.empresaId());
        final Servico servico = servicoDTO.toEntity(usuarios, empresa);
        return servicoRespository.save(servico);
    }

    public void alterar(Integer id, SalvarOuAlterarServicoDTO servicoDTO) {
        buscarPorId(id);
        final List<Usuario> usuarios = usuarioService.buscarUsuariosPorIds(servicoDTO.idsUsuarios());
        final Empresa empresa = empresaService.buscarEmpresaPorId(servicoDTO.empresaId());
        final Servico servico = servicoDTO.toEntity(usuarios, empresa);
        servico.setId(id);
        servicoRespository.save(servico);
    }

    public void deletar(Integer id) {
        final Servico servico = buscarPorId(id);
        servico.setAtivo(false);
        this.servicoRespository.save(servico);
    }

    public Duration calcularDuracaoServicos(List<Servico> servicos) {
        return servicos.stream()
                .map(Servico::getTempo) //Transformando uma lista de Servicos em uma lista de tempos de serviços
                .map(tempoServico -> Duration.ofHours(tempoServico.getHour()).plusMinutes(tempoServico.getMinute())) //Transformando tempo de serviços (que está em hora) em uma Duration
                .reduce(Duration.ZERO, Duration::plus); //Somando as Durations
    }

    public List<Servico> buscarServicosPorUsarioId(Integer usuarioId) {
        return this.servicoRespository.findAllByUsuarioIdAndAtivo(usuarioId);
    }

    public List<Servico> buscarServicosPorEmpresaId(Long empresaId) {
        return this.servicoRespository.findAllByEmpresaIdAndAtivo(empresaId);
    }

    public List<Servico> buscarServicosPorEmpresaIdEUsuarioId(Long empresaId, Long usuarioId) {
        return this.servicoRespository.findAllByEmpresaIdAndUsuarioIdAndAtivo(empresaId, usuarioId);
    }

    public Map<String, Long> buscarServicosMaisRealizados(Long empresaId, Long funcionarioId) {
        List<Object[]> results;

        if (funcionarioId != null && funcionarioId != 0) {
            results = servicoRespository.findServicosCountByEmpresaIdAndFuncionarioId(empresaId, funcionarioId);
        } else {
            results = servicoRespository.findServicosCountByEmpresaId(empresaId);
        }

        final Map<String, Long> topServicos = new LinkedHashMap<>();
        for (Object[] row : results) {
            final String nomeServico = (String) row[0];
            final Long totalServicos = (Long) row[1];
            topServicos.put(nomeServico, totalServicos);
        }

        return topServicos;
    }

    public Map<String, BigDecimal> buscarFaturamentoSemestral(Long empresaId, Long funcionarioId) {
        List<Object[]> results;

        if (funcionarioId != null && funcionarioId != 0) {
            results = servicoRespository.findFaturamentoSemestralByEmpresaIdAndFuncionarioId(empresaId, funcionarioId);
        } else {
            results = servicoRespository.findFaturamentoSemestralByEmpresaId(empresaId);
        }

        final Map<String, BigDecimal> faturamentoSemestral = new LinkedHashMap<>();
        for (Object[] row : results) {
            final String mes = (String) row[0];
            final BigDecimal faturamento = (BigDecimal) row[1];
            faturamentoSemestral.put(mes, faturamento);
        }

        return faturamentoSemestral;
    }
}