package br.com.timesync.controllers;

import br.com.timesync.services.AgendamentoService;
import br.com.timesync.services.ServicoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

    private final AgendamentoService agendamentoService;
    private final ServicoService servicoService;

    @GetMapping("/buscarQuantidadeDeAgendamentosPorMes")
    public ResponseEntity<Map<String, Long>> buscarQuantidadeDeAgendamentosPorMes(@RequestParam Long empresaId, @RequestParam Long funcionarioId) {
        return ResponseEntity.ok().body(agendamentoService.buscarQuantidadeDeAgendamentosPorMes(empresaId, funcionarioId));
    }

    @GetMapping("/buscarServicosMaisRealizados")
    public ResponseEntity<Map<String, Long>> buscarServicosMaisRealizados(@RequestParam Long empresaId, @RequestParam Long funcionarioId) {
        return ResponseEntity.ok().body(servicoService.buscarServicosMaisRealizados(empresaId, funcionarioId));
    }

    @GetMapping("/buscarFaturamentoSemestral")
    public ResponseEntity<Map<String, BigDecimal>> buscarFaturamentoSemestral(@RequestParam Long empresaId, @RequestParam Long funcionarioId) {
        return ResponseEntity.ok().body(servicoService.buscarFaturamentoSemestral(empresaId, funcionarioId));
    }

}