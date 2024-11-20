package br.com.timesync.controllers;

import br.com.timesync.dto.BuscarUsuarioDTO;
import br.com.timesync.dto.SalvarOuAlterarUsuarioDTO;
import br.com.timesync.entities.Usuario;
import br.com.timesync.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController {

    private final UsuarioService usuarioSevice;

    @GetMapping("/{id}")
    public ResponseEntity<BuscarUsuarioDTO> buscarPorId(@PathVariable Long id) {
        final Usuario usuario = usuarioSevice.buscarPorId(id);
        final BuscarUsuarioDTO dto = BuscarUsuarioDTO.toDTO(usuario);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/buscarClientes")
    public ResponseEntity<List<BuscarUsuarioDTO>> buscarClientes() {
        final var usuarios = usuarioSevice.buscarClientes();
        final var usuariosDTO = usuarios.stream().map(BuscarUsuarioDTO::toDTO).toList();
        return ResponseEntity.ok().body(usuariosDTO);
    }

    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody SalvarOuAlterarUsuarioDTO usuarioDTO) {
        final Usuario usuario = this.usuarioSevice.salvar(usuarioDTO);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Long id, @RequestBody SalvarOuAlterarUsuarioDTO salvarOuAlterarUsuarioDTO) {
        this.usuarioSevice.alterar(id, salvarOuAlterarUsuarioDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        this.usuarioSevice.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
