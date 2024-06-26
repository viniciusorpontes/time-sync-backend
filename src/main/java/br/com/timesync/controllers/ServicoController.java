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
@CrossOrigin
public class ServicoController {

    private final ServicoService servicoService;

    @GetMapping("/buscarTodos")
    public ResponseEntity<List<Servico>> buscarTodos() {
        return ResponseEntity.ok().body(this.servicoService.buscarTodos());
    }

    @GetMapping("/buscarPorId")
    public ResponseEntity<Servico> buscarPorId(@RequestParam Integer id) {
        return ResponseEntity.ok().body(servicoService.buscarPorId(id));
    }

    @GetMapping("/buscarServicosPorUsuarioId")
    public ResponseEntity<List<Servico>> buscarServicosPorUsuarioId(@RequestParam Integer usuarioId) {
        return ResponseEntity.ok().body(servicoService.buscarServicosPorUsuarioId(usuarioId));
    }

    @PostMapping("/salvar")
    public ResponseEntity<Servico> salvar(@RequestBody Servico servico) {
        servico = this.servicoService.salvar(servico);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(servico.getId()).toUri();
        return ResponseEntity.created(uri).body(servico);
    }

}