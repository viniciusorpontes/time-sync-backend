package br.com.timesync.dto;

import br.com.timesync.entities.Empresa;
import br.com.timesync.entities.Servico;
import br.com.timesync.entities.Usuario;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

public record SalvarOuAlterarServicoDTO(Long empresaId, String nome, LocalTime tempo, BigDecimal valor, List<Long> idsUsuarios) {

    public Servico toEntity(List<Usuario> usuarios, Empresa empresa) {
        final Servico servico = new Servico();
        servico.setNome(nome);
        servico.setTempo(tempo);
        servico.setValor(valor);
        servico.setUsuarios(usuarios);
        servico.setAtivo(true);
        servico.setEmpresa(empresa);
        return servico;
    }

}