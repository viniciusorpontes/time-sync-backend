package br.com.timesync.repositories;

import br.com.timesync.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    UserDetails findUserDetailsByEmail(String email);

    @Query(value = "SELECT * FROM usuarios s " +
            "WHERE s.tipo = 1 " +
            "  AND s.ativo IS TRUE",
            nativeQuery = true)
    List<Usuario> buscarClientes();
}
