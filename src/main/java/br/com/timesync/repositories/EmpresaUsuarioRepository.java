package br.com.timesync.repositories;

import br.com.timesync.entities.EmpresaUsuario;
import br.com.timesync.entities.EmpresaUsuarioId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaUsuarioRepository extends JpaRepository<EmpresaUsuario, EmpresaUsuarioId> {

    @Query(value = """
            SELECT eu FROM EmpresaUsuario eu
            INNER JOIN FETCH eu.empresa e
            INNER JOIN FETCH eu.usuario u
            WHERE e.id = :empresaId
              AND eu.ativo""")
    List<EmpresaUsuario> findByEmpresaId(Long empresaId);

}
