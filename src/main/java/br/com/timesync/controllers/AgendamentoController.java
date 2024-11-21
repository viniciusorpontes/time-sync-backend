package br.com.timesync.controllers;

import br.com.timesync.dto.AlterarAgendamentoDTO;
import br.com.timesync.dto.BuscarAgendamentoDTO;
import br.com.timesync.dto.SalvarOuAlterarAgendamentoDTO;
import br.com.timesync.entities.Agendamento;
import br.com.timesync.services.AgendamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/agendamentos")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @GetMapping("/{id}")
    public ResponseEntity<BuscarAgendamentoDTO> buscarPorId(@PathVariable Integer id) {
        final Agendamento agendamento = agendamentoService.buscarPorId(id);
        return ResponseEntity.ok().body(BuscarAgendamentoDTO.toDTO(agendamento));
    }

    @GetMapping("/buscarAgendamentosPorEmpresaId")
    public ResponseEntity<List<BuscarAgendamentoDTO>> buscarAgendamentosPorEmpresaId(@RequestParam Long empresaId) {
        return ResponseEntity.ok().body(agendamentoService.buscarAgendamentosPorEmpresaId(empresaId));
    }

    @PostMapping
    public ResponseEntity<BuscarAgendamentoDTO> salvar(@RequestBody SalvarOuAlterarAgendamentoDTO salvarOuAlterarAgendamentoDTO) {
        final Agendamento agendamento = this.agendamentoService.salvar(salvarOuAlterarAgendamentoDTO);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(agendamento.getId()).toUri();
        return ResponseEntity.created(uri).body(BuscarAgendamentoDTO.toDTO(agendamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuscarAgendamentoDTO> alterar(@PathVariable Integer id, @RequestBody SalvarOuAlterarAgendamentoDTO salvarOuAlterarAgendamentoDTO) {
        final Agendamento agendamento = this.agendamentoService.alterar(id, salvarOuAlterarAgendamentoDTO);
        return ResponseEntity.ok().body(BuscarAgendamentoDTO.toDTO(agendamento));
    }

    @PutMapping("/alterarDataAgendamento/{id}")
    public ResponseEntity<BuscarAgendamentoDTO> alterarDataAgendamento(@PathVariable Integer id, @RequestBody AlterarAgendamentoDTO dto) {
        final Agendamento agendamento = this.agendamentoService.alterarDataAgendamento(id, dto);
        return ResponseEntity.ok().body(BuscarAgendamentoDTO.toDTO(agendamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        this.agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}