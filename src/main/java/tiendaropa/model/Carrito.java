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
@Table(name = "carrito")
public class Carrito implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "usuarioid")
    private Usuario usuario;

    @OneToMany(mappedBy = "carrito", fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    Set<LineaCarrito> lineascarrito = new HashSet<>();

    // Constructor vacío necesario para JPA/Hibernate.
    // No debe usarse desde la aplicación.
    public Carrito() {}

    // Al crear un pedido lo asociamos automáticamente a un usuario
    public Carrito(Usuario usuario) {
        setUsuario(usuario);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        // Comprueba si el usuario ya está establecido
        if(this.usuario != usuario) {
            this.usuario = usuario;
            usuario.setCarrito(this);
        }
    }

    public Set<LineaCarrito> getLineascarrito() {
        return lineascarrito;
    }

    public void addLineascarrito(LineaCarrito lineaCarrito) {
        if (lineascarrito.contains(lineaCarrito)) return;
        lineascarrito.add(lineaCarrito);
        if (lineaCarrito.getCarrito() != this) {
            lineaCarrito.setCarrito(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carrito carrito = (Carrito) o;
        if (id != null && carrito.id != null)
            // Si tenemos los ID, comparamos por ID
            return Objects.equals(id, carrito.id);
        // si no comparamos por campos obligatorios
        return usuario.equals(carrito.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario);
    }
}
