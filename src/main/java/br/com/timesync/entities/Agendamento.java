package br.com.timesync.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "agendamentos")
public class Agendamento {

    public Agendamento(LocalDateTime dataChegada, LocalDateTime dataSaida, List<Servico> servicos, Usuario cliente, Usuario consumidor) {
        this.dataChegada = dataChegada;
        this.dataSaida = dataSaida;
        this.ativo = Boolean.TRUE;
        this.servicos = servicos;
        this.cliente = cliente;
        this.consumidor = consumidor;
    }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "data_chegada", nullable = false)
    private LocalDateTime dataChegada;

    @Column(name = "data_saida", nullable = false)
    private LocalDateTime dataSaida;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @ManyToMany
    @JoinTable(name = "agendamentos_servicos",
            joinColumns = @JoinColumn(name = "agendamento_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    @ToString.Exclude
    private List<Servico> servicos;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    @ManyToOne
    @JoinColumn(name = "consumidor_id")
    private Usuario consumidor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agendamento servico)) return false;
        return Objects.equals(id, servico.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
