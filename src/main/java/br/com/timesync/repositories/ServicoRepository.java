package br.com.timesync.repositories;

import br.com.timesync.entities.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {

    @Query(value = "SELECT * FROM servicos s " +
            "INNER JOIN servicos_usuarios su ON s.id = su.servico_id " +
            "WHERE su.usuario_id = :usuario_id",
            nativeQuery = true)
    List<Servico> findByUsuarioId(@Param(value = "usuario_id") Integer usuario_id);

}