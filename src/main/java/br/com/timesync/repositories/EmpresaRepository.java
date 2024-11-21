package br.com.timesync.repositories;

import br.com.timesync.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    @Query("SELECT e FROM Empresa e " +
            "INNER JOIN FETCH e.usuariosEmpresas ue " +
            "INNER JOIN FETCH ue.usuario u " +
            "WHERE u.id = :usuarioId ")
    List<Empresa> buscarEmpresasPorUsuarioId(Long usuarioId);

}
