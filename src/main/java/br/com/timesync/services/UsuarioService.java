package br.com.timesync.services;

import br.com.timesync.dto.SalvarOuAlterarUsuarioDTO;
import br.com.timesync.entities.Usuario;
import br.com.timesync.exceptions.ObjectNotFoundException;
import br.com.timesync.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario buscarPorId(Integer usuarioId) {
        return this.usuarioRepository.findById(usuarioId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Id %s não encontrado", usuarioId)));
    }

    public List<Usuario> buscarUsuariosPorIds(List<Integer> ids) {
        return this.usuarioRepository.findAllById(ids);
    }

    public List<Usuario> buscarClientes() {
        return this.usuarioRepository.buscarClientes();
    }

    public UserDetails buscarUserDetailsPorEmail(String email) {
        return this.usuarioRepository.findUserDetailsByEmail(email);
    }

    public Usuario salvar(SalvarOuAlterarUsuarioDTO usuarioDTO) {
        return this.usuarioRepository.save(usuarioDTO.toEntity());
    }

    public void alterar(Integer id, SalvarOuAlterarUsuarioDTO salvarOuAlterarUsuarioDTO) {
        buscarPorId(id);
        final Usuario usuario = salvarOuAlterarUsuarioDTO.toEntity();
        usuario.setId(id);
        this.usuarioRepository.save(usuario);
    }

    public void deletar(Integer id) {
        final Usuario usuario = buscarPorId(id);
        usuario.setAtivo(false);
        this.usuarioRepository.save(usuario);
    }

}