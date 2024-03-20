package tiendaropa.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String nombre;
    private String apellidos;
    @NotNull
    private String password;
    private String telefono;
    private Integer codigopostal;
    private String pais;
    private String poblacion;
    private String direccion;
    private boolean admin = false;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    Set<Pedido> pedidos = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    Set<Comentario> comentarios = new HashSet<>();

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Carrito carrito;

    // Constructor vacío necesario para JPA/Hibernate.
    // No debe usarse desde la aplicación.
    public Usuario() {}

    // Constructor público con los atributos obligatorios.
    public Usuario(String email, String password, String nombre) {
        this.email = email;
        this.nombre = nombre;
        this.password = password;
    }

    // Getters y setters atributos básicos

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(Integer codigopostal) {
        this.codigopostal = codigopostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Set<Pedido> getPedidos() {
        return pedidos;
    }

    public void addPedido(Pedido pedido) {
        // Si el pedido ya está en la lista, no lo añadimos
        if (pedidos.contains(pedido)) return;
        // Añadimos el pedido a la lista
        pedidos.add(pedido);
        // Establecemos la relación inversa del usuario en el pedido
        if (pedido.getUsuario() != this) {
            pedido.setUsuario(this);
        }
    }

    public Set<Comentario> getComentarios() {
        return comentarios;
    }

    public void addComentarios(Comentario comentario) {
        if (comentarios.contains(comentario)) return;
        comentarios.add(comentario);
        if (comentario.getUsuario() != this) {
            comentario.setUsuario(this);
        }
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;

        // Si tenemos los ID, comparamos por ID
        if (id != null && usuario.id != null) {
            return Objects.equals(id, usuario.id);
        }

        // Si no tenemos ID, comparamos por campos obligatorios
        return email.equals(usuario.email) &&
                nombre.equals(usuario.nombre) &&
                password.equals(usuario.password);
    }

    @Override
    public int hashCode() {
        // Generamos un hash basado en los campos obligatorios
        return Objects.hash(email, nombre, password);
    }
}
