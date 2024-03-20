package tiendaropa.dto;
import java.io.Serializable;
import java.util.Objects;

public class LineaCarritoData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private int cantidad;
    private Long pedidoId;
    private Long productoId;

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

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }
    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LineaCarritoData)) return false;
        LineaCarritoData lineaCarritoData = (LineaCarritoData) o;
        return Objects.equals(id, lineaCarritoData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
