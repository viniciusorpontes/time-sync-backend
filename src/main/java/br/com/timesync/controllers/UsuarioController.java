package br.com.timesync.controllers;

import br.com.timesync.dto.LoginDTO;
import br.com.timesync.entities.Usuario;
import br.com.timesync.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioSevice;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/buscarPorId")
    public ResponseEntity<Usuario> buscarPorId(@RequestParam Integer id) {
        return ResponseEntity.ok().body(usuarioSevice.buscarPorId(id));
    }

    @PostMapping("/salvar")
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {
        usuario = this.usuarioSevice.salvar(usuario);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO login) {
        var token = new UsernamePasswordAuthenticationToken(login.email(), login.senha());
        var authentication = authenticationManager.authenticate(token);
        return ResponseEntity.ok().build();
    }

}
