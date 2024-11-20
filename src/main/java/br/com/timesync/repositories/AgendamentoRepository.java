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
            "WHERE a.empresa.id = :empresaId " +
            "AND a.ativo = TRUE")
    List<Agendamento> findAllByEmpresaId(@Param("empresaId") Long empresaId);

    @Query(value = "SELECT TO_CHAR(DATE_TRUNC('month', data_chegada), 'TMMonth') AS mes, " +
            "COUNT(*) AS total_agendamentos " +
            "FROM agendamentos " +
            "WHERE data_chegada >= NOW() - INTERVAL '4 months' " +
            "  AND empresa_id = :empresaId " +
            "GROUP BY DATE_TRUNC('month', data_chegada) " +
            "ORDER BY DATE_TRUNC('month', data_chegada)", nativeQuery = true)
    List<Object[]> findAgendamentosCountByMonthAndEmpresaId(@Param("empresaId") Long empresaId);

    @Query(value = "SELECT TO_CHAR(DATE_TRUNC('month', data_chegada), 'TMMonth') AS mes, " +
            "COUNT(*) AS total_agendamentos " +
            "FROM agendamentos " +
            "WHERE data_chegada >= NOW() - INTERVAL '4 months' " +
            "  AND empresa_id = :empresaId " +
            "  AND cliente_id = :funcionarioId " +
            "GROUP BY DATE_TRUNC('month', data_chegada) " +
            "ORDER BY DATE_TRUNC('month', data_chegada)", nativeQuery = true)
    List<Object[]> findAgendamentosCountByMonthAndEmpresaIdAndFuncionarioId(@Param("empresaId") Long empresaId, @Param("funcionarioId") Long funcionarioId);

}