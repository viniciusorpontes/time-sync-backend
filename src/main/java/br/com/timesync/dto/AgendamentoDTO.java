package br.com.timesync.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendamentoDTO {

    private LocalDateTime dataChegada;
    private List<Integer> idsServicos;
    private Integer clienteId;
    private Integer consumidorId;

}
