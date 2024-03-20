package tiendaropa.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuarioid")
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    Set<LineaPedido> lineaspedido = new HashSet<>();

    // Constructor vacío necesario para JPA/Hibernate.
    // No debe usarse desde la aplicación.
    public Pedido() {}

    // Al crear un pedido lo asociamos automáticamente a un usuario
    public Pedido(Usuario usuario) {
        setUsuario(usuario);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        // Comprueba si el usuario ya está establecido
        if(this.usuario != usuario) {
            this.usuario = usuario;
            // Añade el pedido a la lista de pedidos del usuario
            usuario.addPedido(this);
        }
    }

    public Set<LineaPedido> getLineaspedido() {
        return lineaspedido;
    }

    public void addLineaspedido(LineaPedido lineaPedido) {
        if (lineaspedido.contains(lineaPedido)) return;
        lineaspedido.add(lineaPedido);
        if (lineaPedido.getPedido() != this) {
            lineaPedido.setPedido(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        if (id != null && pedido.id != null)
            // Si tenemos los ID, comparamos por ID
            return Objects.equals(id, pedido.id);
        // si no comparamos por campos obligatorios
        return usuario.equals(pedido.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario);
    }
}
