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

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioSevice;

    @GetMapping("/{id}")
    public ResponseEntity<BuscarUsuarioDTO> buscarPorId(@PathVariable Integer id) {
        final Usuario usuario = usuarioSevice.buscarPorId(id);
        final BuscarUsuarioDTO dto = BuscarUsuarioDTO.toDTO(usuario);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<Usuario> salvar(@RequestBody SalvarOuAlterarUsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioDTO.toEntity();
        usuario = this.usuarioSevice.salvar(usuario);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Integer id, @RequestBody SalvarOuAlterarUsuarioDTO usuarioDTO) {
        final Usuario usuario = this.usuarioSevice.alterar(id, usuarioDTO);
        return ResponseEntity.ok().body(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        this.usuarioSevice.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
