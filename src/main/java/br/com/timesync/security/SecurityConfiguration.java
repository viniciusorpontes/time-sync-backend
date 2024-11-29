package br.com.timesync.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable) // Desabilitando segurança contra CSRF
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Alterando política de segurança da aplicação para STATELESS
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, "/usuarios").permitAll(); // Permite POST em /usuarios
                    req.requestMatchers(HttpMethod.GET, "/empresas").permitAll(); // Permite GET em /empresas
                    req.requestMatchers("/login").permitAll(); // Permite acesso ao loginreq.anyRequest().authenticated();
                    req.anyRequest().authenticated(); // Bloqueando todos os outros endpoints da aplicação
                })
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) // Alterando ordem dos filtros para nosso filtro personalizado rodar primeiro
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Algoritmo responsável por criptografar as senhas da aplicação
        return new BCryptPasswordEncoder();
    }

}
