package tiendaropa.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "lineacarrito")
public class LineaCarrito implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidad;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "carritoid")
    private Carrito carrito;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "productoid")
    private Producto producto;

    public LineaCarrito() {}

    public LineaCarrito(Carrito carrito, Producto producto) {
        setCarrito(carrito);
        setProducto(producto);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        if(this.carrito != carrito) {
            this.carrito = carrito;
            this.carrito.setId(carrito.getId());
            this.carrito.setUsuario(carrito.getUsuario());
            carrito.addLineascarrito(this);
        }
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        if(this.producto != producto) {
            this.producto = producto;
            producto.addLineascarrito(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineaCarrito lineaCarrito = (LineaCarrito) o;
        if (id != null && lineaCarrito.id != null)
            return Objects.equals(id, lineaCarrito.id);
        return carrito.equals(lineaCarrito.carrito) && producto.equals(lineaCarrito.producto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carrito, producto);
    }
}
