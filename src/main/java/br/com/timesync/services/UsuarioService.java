package br.com.timesync.services;

import br.com.timesync.entities.Usuarios;
import br.com.timesync.exceptions.ObjectNotFoundException;
import br.com.timesync.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuarios buscarPorId(Integer usuarioId) {
        return this.usuarioRepository.findById(usuarioId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Id %s não encontrado", usuarioId)));
    }

    public Usuarios buscarPorEmail(String email) {
        return (Usuarios) this.usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Email %s não encontrado", email)));
    }

    public Usuarios salvar(Usuarios usuario) {
        return usuarioRepository.save(usuario);
    }

}