package br.com.timesync.services;

import br.com.timesync.entities.Servico;
import br.com.timesync.exceptions.ObjectNotFoundException;
import br.com.timesync.repositories.ServicoRespository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServicoService {

    private final ServicoRespository servicoRespository;

    public List<Servico> buscarTodos() {
        return this.servicoRespository.findAll();
    }

    public Servico buscarPorId(Integer id) {
        return this.servicoRespository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Id %s não encontrado", id)));
    }

    public Servico salvar(Servico servico) {
        return servicoRespository.save(servico);
    }

}