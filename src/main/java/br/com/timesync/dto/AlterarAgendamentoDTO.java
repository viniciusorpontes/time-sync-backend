package br.com.timesync.dto;

import java.time.LocalDateTime;

public record AlterarAgendamentoDTO(Long usuarioId, LocalDateTime dataChegada) {

}
