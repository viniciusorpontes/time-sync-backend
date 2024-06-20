package br.com.timesync.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AgendamentoDTO(LocalDateTime dataChegada, List<Integer> idsServicos, Integer clienteId, Integer consumidorId) {

}
