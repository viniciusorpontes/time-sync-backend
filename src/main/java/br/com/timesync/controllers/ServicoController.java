package br.com.timesync.controllers;

import br.com.timesync.entities.Servico;
import br.com.timesync.services.ServicoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/servicos")
@AllArgsConstructor
public class ServicoController {

    private final ServicoService servicoService;

    @GetMapping
    public ResponseEntity<List<Servico>> buscarTodos() {
        return ResponseEntity.ok().body(this.servicoService.buscarTodos());
    }

    @GetMapping("{id}")
    public ResponseEntity<Servico> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok().body(servicoService.buscarPorId(id));
    }

    @GetMapping("/buscarServicosPorUsuarioId")
    public ResponseEntity<List<Servico>> buscarServicoPorUsuarioId(@RequestParam Integer usuarioId) {
        return ResponseEntity.ok().body(servicoService.buscarServicosPorUsuarioId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Servico> salvar(@RequestBody Servico servico) {
        servico = this.servicoService.salvar(servico);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(servico.getId()).toUri();
        return ResponseEntity.created(uri).body(servico);
    }

}