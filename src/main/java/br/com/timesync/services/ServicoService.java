package br.com.timesync.services;

import br.com.timesync.dto.SalvarOuAlterarServicoDTO;
import br.com.timesync.entities.Servico;
import br.com.timesync.entities.Usuario;
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
    private final UsuarioService usuarioService;

    public Servico buscarPorId(Integer id) {
        return this.servicoRespository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Id %s não encontrado", id)));
    }

    public Servico salvar(SalvarOuAlterarServicoDTO servicoDTO) {
        final List<Usuario> usuarios = usuarioService.buscarUsuariosPorIds(servicoDTO.idsUsuarios());
        final Servico servico = servicoDTO.toEntity(usuarios);
        return servicoRespository.save(servico);
    }

    public void alterar(Integer id, SalvarOuAlterarServicoDTO servicoDTO) {
        buscarPorId(id);
        final List<Usuario> usuarios = usuarioService.buscarUsuariosPorIds(servicoDTO.idsUsuarios());
        final Servico servico = servicoDTO.toEntity(usuarios);
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

}