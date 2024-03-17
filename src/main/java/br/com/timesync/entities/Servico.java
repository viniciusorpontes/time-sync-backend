package br.com.timesync.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Servico {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "tempo", nullable = false)
    private LocalTime tempo;

    @Column(name = "valor", nullable = false, scale = 2)
    private BigDecimal valor;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Servico servico)) return false;
        return Objects.equals(id, servico.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}