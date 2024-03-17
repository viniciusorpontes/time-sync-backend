package br.com.timesync.repositories;

import br.com.timesync.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    Optional<Object> findByUsuarioId(Integer usuarioId);

}