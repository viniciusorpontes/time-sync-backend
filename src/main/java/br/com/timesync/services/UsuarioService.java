package br.com.timesync.services;

import br.com.timesync.dto.SalvarOuAlterarUsuarioDTO;
import br.com.timesync.entities.Usuario;
import br.com.timesync.exceptions.ObjectNotFoundException;
import br.com.timesync.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario buscarPorId(Long usuarioId) {
        return this.usuarioRepository.findById(usuarioId).orElseThrow(
                () -> new ObjectNotFoundException(String.format("Id %s não encontrado", usuarioId)));
    }

    public List<Usuario> buscarUsuariosPorIds(List<Long> ids) {
        return this.usuarioRepository.findAllById(ids);
    }

    public List<Usuario> buscarClientes() {
        return this.usuarioRepository.buscarClientes();
    }

    public Usuario buscarUsuarioPorEmail(String email) {
        return this.usuarioRepository.findByEmail(email);
    }

    public Usuario salvar(SalvarOuAlterarUsuarioDTO usuarioDTO) {
        final Usuario usuario = usuarioDTO.toEntity();
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return this.usuarioRepository.save(usuario);
    }

    public void alterar(Long id, SalvarOuAlterarUsuarioDTO salvarOuAlterarUsuarioDTO) {
        buscarPorId(id);
        final Usuario usuario = salvarOuAlterarUsuarioDTO.toEntity();
        usuario.setId(id);
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        this.usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        final Usuario usuario = buscarPorId(id);
        usuario.setAtivo(false);
        this.usuarioRepository.save(usuario);
    }

}