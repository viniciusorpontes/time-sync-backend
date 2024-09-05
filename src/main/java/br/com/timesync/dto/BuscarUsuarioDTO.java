package br.com.timesync.dto;

import br.com.timesync.entities.Usuario;
import br.com.timesync.enums.UsuarioEnum;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuscarUsuarioDTO {

    private Integer id;

    private String cpf;

    private String nome;

    private String email;

    private String telefone;

    private UsuarioEnum tipo;

    public static BuscarUsuarioDTO toDTO(Usuario usuario) {
        return new BuscarUsuarioDTO(
                usuario.getId(),
                usuario.getCpf(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTelefone(),
                usuario.getTipo()
        );
    }

}
