package br.com.timesync.services;

import br.com.timesync.dto.AgendamentoDTO;
import br.com.timesync.entities.Agendamento;
import br.com.timesync.entities.Servicos;
import br.com.timesync.entities.Usuarios;
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
        return this.agendamentoRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Id %s não encontrado", id)));
    }

    public Agendamento salvar(AgendamentoDTO agendamentoDTO) {
        final LocalDateTime dataChegada = agendamentoDTO.getDataChegada();
        final List<Servicos> servicos = getServicos(agendamentoDTO.getIdsServicos());
        final Usuarios cliente = usuarioService.buscarPorId(agendamentoDTO.getClienteId());
        final Usuarios consumidor = usuarioService.buscarPorId(agendamentoDTO.getConsumidorId());

        final Agendamento agendamento = new Agendamento(dataChegada, getDataSaida(dataChegada, servicos), servicos, cliente, consumidor);
        this.agendamentoRepository.save(agendamento);
        return agendamento;
    }

    /** Função que recebe uma lista de ids de Servicos como parâmetro e retorna uma lista de Servicos */
    private List<Servicos> getServicos(List<Integer> idsServicos) {
        return new ArrayList<>(idsServicos
                .stream()
                .map(servicoService::buscarPorId)
                .toList()
        );
    }

    private LocalDateTime getDataSaida(LocalDateTime dataChegada, List<Servicos> servicos) {
        final Duration duracaoServicos = servicoService.calcularDuracaoServicos(servicos);
        return dataChegada.plusMinutes(duracaoServicos.toMinutes());
    }

}