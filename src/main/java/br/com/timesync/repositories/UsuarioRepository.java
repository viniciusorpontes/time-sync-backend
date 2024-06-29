package br.com.timesync.repositories;

import br.com.timesync.entities.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuarios, Integer> {

    Optional<Object> findByEmail(String email);
}
