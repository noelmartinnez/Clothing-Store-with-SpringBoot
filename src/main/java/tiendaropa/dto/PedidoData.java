package tiendaropa.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PedidoData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Date fecha;
    private Long usuarioId;  // Esta es la ID del usuario asociado

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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PedidoData)) return false;
        PedidoData pedidoData = (PedidoData) o;
        return Objects.equals(id, pedidoData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
