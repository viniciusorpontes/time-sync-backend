package br.com.timesync.repositories;

import br.com.timesync.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    List<Servico> findByUsuarioId(Integer usuarioId);

    List<Servico> findAllByAtivoTrue();

}