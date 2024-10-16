package br.com.timesync.dto;

import br.com.timesync.entities.Servico;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class BuscarServicoDTO {

    private Integer id;
    private String nome;
    private LocalTime tempo;
    private BigDecimal valor;
    private List<BuscarUsuarioDTO> usuarios;

    public static BuscarServicoDTO toDTO(Servico servico) {
        return new BuscarServicoDTO(
                servico.getId(),
                servico.getNome(),
                servico.getTempo(),
                servico.getValor(),
                servico.getUsuarios().stream().map(BuscarUsuarioDTO::toDTO).toList()
        );
    }

}
