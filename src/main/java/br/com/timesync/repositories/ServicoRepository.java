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
            "WHERE su.usuario_id = :usuario_id " +
            "  AND s.ativo IS TRUE",
            nativeQuery = true)
    List<Servico> findAllByUsuarioIdAndAtivo(@Param(value = "usuario_id") Integer usuarioId);

    @Query(value = "SELECT s FROM Servico s " +
            "JOIN FETCH s.empresa e " +
            "WHERE e.id = :empresa_id " +
            "  AND s.ativo IS TRUE")
    List<Servico> findAllByEmpresaIdAndAtivo(@Param(value = "empresa_id") Long empresaId);

    @Query(value = "SELECT * FROM servicos s " +
            "INNER JOIN servicos_usuarios su ON s.id = su.servico_id " +
            "WHERE s.empresa_id = :empresa_id " +
            "  AND su.usuario_id = :usuario_id " +
            "  AND s.ativo IS TRUE",
            nativeQuery = true)
    List<Servico> findAllByEmpresaIdAndUsuarioIdAndAtivo(@Param(value = "empresa_id") Long empresaId, @Param(value = "usuario_id") Long usuarioId);

    @Query(value = "SELECT s.nome, COUNT(*) FROM agendamentos_servicos sa " +
            "INNER JOIN public.servicos s on s.id = sa.servico_id " +
            "INNER JOIN public.agendamentos a on a.id = sa.agendamento_id " +
            "WHERE a.empresa_id = :empresaId " +
            "GROUP BY s.nome;",
            nativeQuery = true)
    List<Object[]> findServicosCountByEmpresaId(Long empresaId);

    @Query(value = "SELECT s.nome, COUNT(*) FROM agendamentos_servicos sa " +
            "INNER JOIN public.servicos s on s.id = sa.servico_id " +
            "INNER JOIN public.agendamentos a on a.id = sa.agendamento_id " +
            "WHERE a.empresa_id = :empresaId " +
            "  AND a.cliente_id = :funcionarioId " +
            "GROUP BY s.nome;",
            nativeQuery = true)
    List<Object[]> findServicosCountByEmpresaIdAndFuncionarioId(Long empresaId, Long funcionarioId);

    @Query(value = "SELECT TO_CHAR(DATE_TRUNC('month', a.data_chegada), 'TMMonth') AS mes, " +
            "       SUM(s.valor) " +
            "FROM agendamentos_servicos sa " +
            "         INNER JOIN servicos s on s.id = sa.servico_id " +
            "         INNER JOIN agendamentos a on a.id = sa.agendamento_id " +
            "WHERE a.empresa_id = :empresaId " +
            "  AND a.data_chegada >= NOW() - INTERVAL '6 months' " +
            "GROUP BY DATE_TRUNC('month', a.data_chegada) " +
            "ORDER BY DATE_TRUNC('month', a.data_chegada)",
            nativeQuery = true)
    List<Object[]> findFaturamentoSemestralByEmpresaId(Long empresaId);

    @Query(value = "SELECT TO_CHAR(DATE_TRUNC('month', a.data_chegada), 'TMMonth') AS mes, " +
            "       SUM(s.valor) " +
            "FROM agendamentos_servicos sa " +
            "         INNER JOIN servicos s on s.id = sa.servico_id " +
            "         INNER JOIN agendamentos a on a.id = sa.agendamento_id " +
            "WHERE a.empresa_id = :empresaId " +
            "  AND a.cliente_id = :funcionarioId " +
            "  AND a.data_chegada >= NOW() - INTERVAL '6 months' " +
            "GROUP BY DATE_TRUNC('month', a.data_chegada) " +
            "ORDER BY DATE_TRUNC('month', a.data_chegada)",
            nativeQuery = true)
    List<Object[]> findFaturamentoSemestralByEmpresaIdAndFuncionarioId(Long empresaId, Long funcionarioId);
}