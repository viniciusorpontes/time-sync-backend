package br.com.timesync.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SalvarOuAlterarAgendamentoDTO(LocalDateTime dataChegada, List<Integer> idsServicos, Integer clienteId, Integer consumidorId) {

}
