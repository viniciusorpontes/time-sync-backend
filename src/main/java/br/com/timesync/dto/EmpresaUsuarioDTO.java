package br.com.timesync.dto;

import br.com.timesync.entities.EmpresaUsuario;
import br.com.timesync.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmpresaUsuarioDTO {

    private Long usuarioId;

    private String nome;

    private String email;

    private String telefone;

    private Boolean confirmado;

    private Boolean gestor;

    public static EmpresaUsuarioDTO toDTO(EmpresaUsuario empresaUsuario) {
        return new EmpresaUsuarioDTO(
                empresaUsuario.getId().getUsuarioId(),
                empresaUsuario.getUsuario().getNome(),
                empresaUsuario.getUsuario().getEmail(),
                empresaUsuario.getUsuario().getTelefone(),
                empresaUsuario.getConfirmado(),
                empresaUsuario.getGestor()
        );
    }

    public static EmpresaUsuarioDTO toDTO(EmpresaUsuario empresaUsuario, Usuario usuario) {
        return new EmpresaUsuarioDTO(
                empresaUsuario.getId().getUsuarioId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                empresaUsuario.getConfirmado(),
                empresaUsuario.getGestor()
        );
    }

}
