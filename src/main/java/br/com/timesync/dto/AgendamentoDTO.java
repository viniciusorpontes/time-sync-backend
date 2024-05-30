package br.com.timesync.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AgendamentoDTO(LocalDateTime dataChegad, List<Integer> idsServicos, Integer clienteId, Integer consumidorId) {

}
