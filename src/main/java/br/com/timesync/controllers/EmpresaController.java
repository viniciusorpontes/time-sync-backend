package br.com.timesync.controllers;

import br.com.timesync.dto.*;
import br.com.timesync.entities.Empresa;
import br.com.timesync.services.EmpresaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/empresas")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class EmpresaController {

    private final EmpresaService empresaService;

    @GetMapping("/buscarEmpresasPorUsuarioId")
    public ResponseEntity<List<BuscarEmpresaDTO>> buscarEmpresasPorUsuarioId(@RequestParam Long usuarioId) {
        return ResponseEntity.ok().body(empresaService.buscarEmpresasPorUsuarioId(usuarioId));
    }

    @GetMapping("/buscarUsuariosPorEmpresa")
    public ResponseEntity<List<EmpresaUsuarioDTO>> buscarUsuariosPorEmpresa(@RequestParam Long empresaId) {
        return ResponseEntity.ok().body(empresaService.buscarUsuariosPorEmpresa(empresaId));
    }

    @PostMapping
    public ResponseEntity<BuscarEmpresaDTO> salvar(@RequestBody SalvarEmpresaDTO dto) {
        final Empresa empresa = this.empresaService.salvar(dto);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(empresa.getId()).toUri();
        return ResponseEntity.created(uri).body(BuscarEmpresaDTO.toDTO(empresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuscarEmpresaDTO> alterar(@PathVariable Long id, @RequestBody AlterarEmpresaDTO dto) {
        final Empresa empresa = this.empresaService.alterar(id, dto);
        return ResponseEntity.ok().body(BuscarEmpresaDTO.toDTO(empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        this.empresaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/convidarProdutivo")
    public ResponseEntity<EmpresaUsuarioDTO> convidarProdutivo(@RequestBody ConvidarUsuarioDTO dto) {
        final EmpresaUsuarioDTO empresaUsuarioDTO = this.empresaService.convidarProdutivo(dto);
        return ResponseEntity.ok().body(empresaUsuarioDTO);
    }

    @PutMapping("/alterarProdutivo")
    public ResponseEntity<EmpresaUsuarioDTO> alterarProdutivo(@RequestBody AlterarProdutivoDTO dto) {
        final EmpresaUsuarioDTO empresaUsuarioDTO = this.empresaService.alterarProdutivo(dto);
        return ResponseEntity.ok().body(empresaUsuarioDTO);
    }

    @DeleteMapping("/removerProdutivo/{empresaId}/{email}")
    public ResponseEntity<Void> deletar(@PathVariable Long empresaId,
                                        @PathVariable String email) {
        this.empresaService.removerProdutivo(empresaId, email);
        return ResponseEntity.noContent().build();
    }

}