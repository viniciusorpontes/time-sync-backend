package br.com.timesync.repositories;

import br.com.timesync.entities.Servicos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servicos, Integer> {

    List<Servicos> findByUsuarioId(Integer usuarioId);

}