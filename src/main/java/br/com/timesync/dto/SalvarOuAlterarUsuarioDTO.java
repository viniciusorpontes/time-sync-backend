package br.com.timesync.dto;

import br.com.timesync.entities.Usuario;
import br.com.timesync.enums.UsuarioEnum;

public record SalvarOuAlterarUsuarioDTO(String cpf, String nome, String email, String telefone, UsuarioEnum tipo, String senha) {

    public Usuario toEntity() {
        final Usuario usuario = new Usuario();
        usuario.setCpf(cpf);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setTelefone(telefone);
        usuario.setTipo(tipo);
        usuario.setSenha(senha);
        return usuario;
    }

}
