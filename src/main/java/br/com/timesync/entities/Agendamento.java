package br.com.timesync.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Agendamento {

    public Agendamento(LocalDateTime dataChegada, LocalDateTime dataSaida, List<Servicos> servicos, Usuarios cliente, Usuarios consumidor) {
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
    @JoinTable(name = "agendamento_servico",
            joinColumns = @JoinColumn(name = "agendamento_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    @ToString.Exclude
    private List<Servicos> servicos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Usuarios cliente;

    @ManyToOne
    @JoinColumn(name = "consumidor_id")
    private Usuarios consumidor;

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
