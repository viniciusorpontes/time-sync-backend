package br.com.timesync.security;

import br.com.timesync.services.TokenService;
import br.com.timesync.services.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private final TokenService tokenService;
    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request); // Recupera token enviado no Authorization Header
        /* Caso esteja presente, a aplicação valida o token. Caso não esteja presente, a aplicação deixa passar sem
        autenticação. Passando a responsabilidade de barrar a requisição para o filtro padrão do Spring Security. */
        if (tokenJWT != null) {
            // Busca o e-mail do usuário que está tentando logar na aplicação
            var emailUsuarioLogin = tokenService.getEmailUsuarioLogin(tokenJWT);
            // Busca o Usuario por email
            var usuario = usuarioService.buscarUserDetailsPorEmail(emailUsuarioLogin);
            // Caso o usuário seja encontrado, autentica o usuário
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, null);
            // Seta o Usuario como autenticado
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        /* Chama o próximo filter. No caso da nossa aplicação, chama o Filter padrão do Spring Security que verifica se
        o endpoint necessita ou não de autenticação */
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

}
