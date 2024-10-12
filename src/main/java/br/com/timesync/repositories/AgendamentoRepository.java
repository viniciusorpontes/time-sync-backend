package br.com.timesync.repositories;

import br.com.timesync.entities.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    @Query("SELECT a FROM Agendamento a LEFT JOIN FETCH a.servicos WHERE a.id IN :id ")
    Optional<Agendamento> findById(@Param("id") Integer id);

    @Query("SELECT a FROM Agendamento a " +
           "LEFT JOIN FETCH a.servicos " +
           "WHERE a.cliente.id IN :usuariosIds " +
           "AND a.ativo = TRUE")
    List<Agendamento> findAllByUsuariosIds(@Param("usuariosIds") List<Integer> usuariosIds);

}