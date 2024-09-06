package br.com.timesync.services;

import br.com.timesync.dto.SalvarOuAlterarUsuarioDTO;
import br.com.timesync.entities.Usuario;
import br.com.timesync.exceptions.ObjectNotFoundException;
import br.com.timesync.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario buscarPorId(Integer usuarioId) {
        return this.usuarioRepository.findById(usuarioId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Id %s não encontrado", usuarioId)));
    }

    public UserDetails buscarUsuarioPorEmail(String email) {
        return this.usuarioRepository.findByEmail(email);
    }

    public Usuario salvar(SalvarOuAlterarUsuarioDTO usuarioDTO) {
        return this.usuarioRepository.save(usuarioDTO.toEntity());
    }

    public Usuario alterar(Integer id, SalvarOuAlterarUsuarioDTO usuarioDTO) {
        buscarPorId(id);
        final Usuario usuario;
        usuario = usuarioDTO.toEntity();
        usuario.setId(id);
        return this.usuarioRepository.save(usuario);
    }

    public void deletar(Integer id) {
        final Usuario usuario = buscarPorId(id);
        usuario.setAtivo(false);
        this.usuarioRepository.save(usuario);
    }
}