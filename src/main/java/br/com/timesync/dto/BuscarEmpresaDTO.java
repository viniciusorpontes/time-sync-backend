package br.com.timesync.dto;

import br.com.timesync.entities.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuscarEmpresaDTO {

    private Long id;
    private String nome;
    private String endereco;
    private String telefone;

    public static BuscarEmpresaDTO toDTO(Empresa empresa) {
        return new BuscarEmpresaDTO(
                empresa.getId(),
                empresa.getNome(),
                empresa.getEndereco(),
                empresa.getTelefone()
        );
    }

}
