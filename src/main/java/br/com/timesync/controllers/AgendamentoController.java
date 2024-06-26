package br.com.timesync.controllers;

import br.com.timesync.dto.AgendamentoDTO;
import br.com.timesync.entities.Agendamento;
import br.com.timesync.services.AgendamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/agendamentos")
@AllArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @GetMapping("{id}")
    public ResponseEntity<Agendamento> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok().body(agendamentoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Agendamento> salvar(@RequestBody AgendamentoDTO agendamentoDTO) {
        final Agendamento agendamento = this.agendamentoService.salvar(agendamentoDTO);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(agendamento.getId()).toUri();
        return ResponseEntity.created(uri).body(agendamento);
    }

}