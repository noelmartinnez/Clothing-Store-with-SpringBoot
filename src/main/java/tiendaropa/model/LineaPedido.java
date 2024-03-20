package tiendaropa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "lineapedido")
public class LineaPedido implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date fecha;

    private float precio;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "pedidoid")
    private Pedido pedido;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "productoid")
    private Producto producto;

    public LineaPedido() {}

    public LineaPedido(Pedido pedido, Producto producto) {
        setPedido(pedido);
        setProducto(producto);
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

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        if(this.pedido != pedido) {
            this.pedido = pedido;
            pedido.addLineaspedido(this);
        }
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        if(this.producto != producto) {
            this.producto = producto;
            producto.addLineaspedido(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineaPedido lineaPedido = (LineaPedido) o;
        if (id != null && lineaPedido.id != null)
            return Objects.equals(id, lineaPedido.id);
        return pedido.equals(lineaPedido.pedido) && producto.equals(lineaPedido.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pedido, producto);
    }
}
