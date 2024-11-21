package br.com.timesync.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "empresas_usuarios")
public class EmpresaUsuario {

    public EmpresaUsuario(Empresa empresa, Usuario usuario, Boolean confirmado, Boolean gestor, Boolean ativo) {
        this.id = new EmpresaUsuarioId(empresa.getId(), usuario.getId());
        this.empresa = empresa;
        this.usuario = usuario;
        this.confirmado = confirmado;
        this.gestor = gestor;
        this.ativo = ativo;
    }

    @EmbeddedId
    private EmpresaUsuarioId id;

    @MapsId("empresaId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @MapsId("usuarioId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ColumnDefault("false")
    @Column(name = "confirmado", nullable = false)
    private Boolean confirmado = false;

    @ColumnDefault("false")
    @Column(name = "gestor", nullable = false)
    private Boolean gestor = false;

    @ColumnDefault("true")
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = false;

}