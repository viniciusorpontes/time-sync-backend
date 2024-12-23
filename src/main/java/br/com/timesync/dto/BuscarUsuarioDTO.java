package br.com.timesync.dto;

import br.com.timesync.entities.Usuario;
import br.com.timesync.enums.UsuarioEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuscarUsuarioDTO {

    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private UsuarioEnum tipo;

    public static BuscarUsuarioDTO toDTO(Usuario usuario) {
        return new BuscarUsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getTipo()
        );
    }

}
