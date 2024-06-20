package br.com.timesync.controllers;

import br.com.timesync.dto.LoginRequestDTO;
import br.com.timesync.dto.LoginResponseDTO;
import br.com.timesync.entities.Usuario;
import br.com.timesync.services.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        //Transforma o LoginRequestDTO em um objeto UsernamePasswordAuthenticationToken próprio do Spring Security
        final var authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.senha());
        // Autentica o objeto UsernamePasswordAuthenticationToken e retorna um Usuario
        final var usuario = (Usuario) authenticationManager.authenticate(authenticationToken).getPrincipal();
        // Gera um token para o Usuario logado
        final var tokenJWT = tokenService.gerarToken(usuario);
        // Insere o Token JWT no objeto LoginResponseDTO
        return ResponseEntity.ok(new LoginResponseDTO(tokenJWT));
    }

}
