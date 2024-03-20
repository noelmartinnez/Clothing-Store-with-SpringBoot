package tiendaropa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "comentario")
public class Comentario implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "usuarioid")
    private Usuario usuario;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "productoid")
    private Producto producto;

    public Comentario() {}

    public Comentario(String descripcion,Date fecha,Usuario usuario, Producto producto) {
        setDescripcion(descripcion);
        setFecha(fecha);
        setUsuario(usuario);
        setProducto(producto);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        if(this.usuario != usuario) {
            this.usuario = usuario;
            usuario.addComentarios(this);
        }
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        if(this.producto != producto) {
            this.producto = producto;
            producto.addComentarios(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comentario comentario = (Comentario) o;
        if (id != null && comentario.id != null)
            return Objects.equals(id, comentario.id);
        return usuario.equals(comentario.usuario) && producto.equals(comentario.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, producto);
    }
}
