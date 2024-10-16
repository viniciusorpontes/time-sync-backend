package br.com.timesync.dto;

import br.com.timesync.entities.Agendamento;
import br.com.timesync.entities.Servico;
import br.com.timesync.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class BuscarAgendamentoDTO {

    private static final String TITULO = "Agendamento do dia %s";
    private static final String DESCRICAO = "Serviços: %s";

    private Integer id;

    private String titulo;

    private String descricao;

    private LocalDateTime dataInicio;

    private LocalDateTime dataFim;

    private List<BuscarServicoDTO> servicos;

    private Integer responsavelId;

    private String responsavelNome;

    private Integer consumidorId;

    public static BuscarAgendamentoDTO toDTO(Agendamento agendamento) {
        final List<Servico> servicos = agendamento.getServicos();

        final String servicosEmString = String.join(
                "-",
                servicos.stream().map(Servico::getNome).toList()
        );

        return new BuscarAgendamentoDTO(
                agendamento.getId(),
                String.format(TITULO, DateUtil.formatarData(agendamento.getDataChegada())),
                String.format(DESCRICAO, servicosEmString),
                agendamento.getDataChegada(),
                agendamento.getDataSaida(),
                servicos.stream().map(BuscarServicoDTO::toDTO).toList(),
                agendamento.getCliente().getId(),
                agendamento.getCliente().getNome(),
                agendamento.getConsumidor().getId()
        );
    }

}
