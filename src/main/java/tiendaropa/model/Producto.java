package tiendaropa.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private float precio;
    @NotNull
    private Integer stock;
    private String numref;
    private boolean destacado = false;
    private Long categoriaid;

    // Al eliminar las lineasPedido de los pedidos de un usuario, se eliminarán las lineasPedido también de esta lista.
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    Set<LineaPedido> lineaspedido = new HashSet<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    Set<LineaCarrito> lineascarrito = new HashSet<>();

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    Set<Comentario> comentarios = new HashSet<>();

    private String img;

    public String getImg() {
        return img;
    }
    public void setImg(String urlImagen) {
        this.img = urlImagen;
    }

    public Producto() {
    }

    public Producto(String nombre, float precio, Integer stock, String numref, boolean destacado, Long categoriaid) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.numref = numref;
        this.destacado = destacado;
        this.categoriaid = categoriaid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getNumref() {
        return numref;
    }
    public void setNumref(String numref) {
        this.numref = numref;
    }

    public boolean getDestacado() {
        return destacado;
    }
    public void setDestacado(boolean destacado) {
        this.destacado = destacado;
    }

    public Long getCategoriaid() {
        return categoriaid;
    }

    public void setCategoriaid(Long categoriaid) {
        this.categoriaid = categoriaid;
    }

    public Set<LineaPedido> getLineaspedido() {
        return lineaspedido;
    }

    public void addLineaspedido(LineaPedido lineaPedido) {
        if (lineaspedido.contains(lineaPedido)) return;
        lineaspedido.add(lineaPedido);
        if (lineaPedido.getProducto() != this) {
            lineaPedido.setProducto(this);
        }
    }

    public Set<LineaCarrito> getLineascarrito() {
        return lineascarrito;
    }

    public void addLineascarrito(LineaCarrito lineaCarrito) {
        if (lineascarrito.contains(lineaCarrito)) return;
        lineascarrito.add(lineaCarrito);
        if (lineaCarrito.getProducto() != this) {
            lineaCarrito.setProducto(this);
        }
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public void addComentarios(Comentario comentario) {
        if (comentarios.contains(comentario)) return;
        comentarios.add(comentario);
        if (comentario.getProducto() != this) {
            comentario.setProducto(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto )) return false;
        return id != null && id.equals(((Producto) o).id);
    }
    @Override
    public int hashCode(){
        // Generamos un hash basado en los campos obligatorios
        return Objects.hash(nombre, precio, stock);
    }
}
