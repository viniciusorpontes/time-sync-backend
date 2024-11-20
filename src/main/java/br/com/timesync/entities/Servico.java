package br.com.timesync.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "servicos")
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "servicos_usuarios",
            joinColumns = @JoinColumn(name = "servico_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    @ToString.Exclude
    private List<Usuario> usuarios;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @ManyToOne
    private Empresa empresa;

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