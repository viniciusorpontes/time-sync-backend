package br.com.timesync.controllers;

import br.com.timesync.dto.BuscarServicoDTO;
import br.com.timesync.dto.SalvarOuAlterarServicoDTO;
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
@CrossOrigin(origins = "http://localhost:3000")
public class ServicoController {

    private final ServicoService servicoService;

    @GetMapping("/{id}")
    public ResponseEntity<BuscarServicoDTO> buscarPorId(@PathVariable Integer id) {
        final Servico servico = servicoService.buscarPorId(id);
        final BuscarServicoDTO dto = BuscarServicoDTO.toDTO(servico);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody SalvarOuAlterarServicoDTO servicoDTO) {
        final Servico servico = this.servicoService.salvar(servicoDTO);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(servico.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody SalvarOuAlterarServicoDTO servicoDTO) {
        this.servicoService.alterar(id, servicoDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        this.servicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscarServicosPorUsarioId")
    public ResponseEntity<List<BuscarServicoDTO>> buscarServicosPorUsarioId(@RequestParam Integer usuarioId) {
        final var servicos = servicoService.buscarServicosPorUsarioId(usuarioId);
        final var servicosDTO = servicos.stream().map(BuscarServicoDTO::toDTO).toList();
        return ResponseEntity.ok().body(servicosDTO);
    }

}