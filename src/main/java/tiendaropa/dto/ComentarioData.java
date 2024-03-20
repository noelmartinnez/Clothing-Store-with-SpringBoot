package tiendaropa.dto;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ComentarioData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String descripcion;
    private Date fecha;
    private Long usuarioId;
    private Long productoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineaCarritoData)) return false;
        ComentarioData comentarioData = (ComentarioData) o;
        return Objects.equals(id, comentarioData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
