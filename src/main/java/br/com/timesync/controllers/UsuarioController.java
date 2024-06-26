package br.com.timesync.controllers;

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
@CrossOrigin
public class UsuarioController {

    private final UsuarioService usuarioSevice;

    @GetMapping("/buscarTodos")
    public ResponseEntity<List<Usuario>> buscarTodos() {
        return ResponseEntity.ok().body(usuarioSevice.buscarTodos());
    }

    @GetMapping("/buscarPorEmail")
    public ResponseEntity<Usuario> buscarPorEmail(@RequestParam String email) {
        return ResponseEntity.ok().body(usuarioSevice.buscarPorEmail(String.valueOf(email)));
    }

    @PostMapping("/salvar")
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
        usuario = this.usuarioSevice.salvar(usuario);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

}
