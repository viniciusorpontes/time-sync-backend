package br.com.timesync.services;

import br.com.timesync.entities.Usuario;
import br.com.timesync.exceptions.ObjectNotFoundException;
import br.com.timesync.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public Usuario buscarPorId(Integer usuarioId) {
        return this.usuarioRepository.findById(usuarioId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Id %s não encontrado", usuarioId)));
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email);
    }

}