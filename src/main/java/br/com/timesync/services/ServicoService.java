package br.com.timesync.services;

import br.com.timesync.entities.Servicos;
import br.com.timesync.exceptions.ObjectNotFoundException;
import br.com.timesync.repositories.ServicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@AllArgsConstructor
public class ServicoService {

    private final ServicoRepository servicoRespository;

    public List<Servicos> buscarTodos() {
        return this.servicoRespository.findAll();
    }

    public Servicos buscarPorId(Integer id) {
        return this.servicoRespository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Id %s não encontrado", id)));
    }

    public List<Servicos> buscarServicosPorUsuarioId(Integer IdUsuario) {
        return this.servicoRespository.findByUsuarioId(IdUsuario);
    }

    public Servicos salvar(Servicos servico) {
        return servicoRespository.save(servico);
    }

    public Duration calcularDuracaoServicos(List<Servicos> servicos) {
        return servicos.stream()
                .map(Servicos::getTempo) //Transformando uma lista de Servicos em uma lista de tempos de serviços
                .map(tempoServico -> Duration.ofHours(tempoServico.getHour()).plusMinutes(tempoServico.getMinute())) //Transformando tempo de serviços (que está em hora) em uma Duration
                .reduce(Duration.ZERO, Duration::plus); //Somando as Durations
    }
}