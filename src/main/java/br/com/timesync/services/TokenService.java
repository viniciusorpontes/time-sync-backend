package br.com.timesync.services;

import br.com.timesync.entities.Usuario;
import br.com.timesync.repositories.UsuarioRepository;
import br.com.timesync.utils.ConstanteUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class TokenService implements UserDetailsService {

    // Carregando secret do application.properties
    @Value("${api.security.token.secret}")
    private String secret;

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findUserDetailsByEmail(email);
    }

    // Método responsável por gerar Tokens
    public String gerarToken(Usuario usuario) {
        try {
            final var algoritmo = Algorithm.HMAC256(secret); // Senha da aplicação
            return JWT.create()
                    .withIssuer(ConstanteUtil.getNomeAplicacao()) // Nome da aplicação para identificação
                    .withSubject(usuario.getEmail()) // E-Mail do usuário retornado no token
                    .withExpiresAt(dataExpiracao()) // Expiração do Token de acesso
                    .sign(algoritmo); // Indica qual algoritmo será responsável por gerar o token
        } catch (JWTCreationException exception){
            throw new RuntimeException("Erro ao gerar token JWT", exception);
        }
    }

    // Método responsável por validar o token
    public String getEmailUsuarioLogin(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret); //Senha da aplicação
            return JWT.require(algoritmo) // Indica qual algoritmo será responsável por gerar o token
                    .withIssuer(ConstanteUtil.getNomeAplicacao()) // Nome da aplicação para identificação
                    .build()
                    .verify(tokenJWT) // Verifica se o Token JWT recebido é válido
                    .getSubject(); // Retorna o email do Usuario, caso o Token JWT e o Usuario forem válidos
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado.");
        }
    }

    private Instant dataExpiracao() {
        // Um Token gerado ficará válido pelo tempo máximo de 2 horas
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of(ConstanteUtil.getFusoHorarioBrasil()));
    }

}
