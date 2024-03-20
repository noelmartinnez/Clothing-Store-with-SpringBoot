package tiendaropa.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class LineaPedidoData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Date fecha;
    private float precio;
    private Long pedidoId;
    private Long productoId;

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
        if (!(o instanceof LineaPedidoData)) return false;
        LineaPedidoData lineaPedidoData = (LineaPedidoData) o;
        return Objects.equals(id, lineaPedidoData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
