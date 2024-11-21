package br.com.timesync.dto;

import java.time.LocalDateTime;
import java.util.List;

public record SalvarOuAlterarAgendamentoDTO(Long empresaId, LocalDateTime dataChegada, List<Integer> idsServicos, Long clienteId, Long consumidorId) {

}
