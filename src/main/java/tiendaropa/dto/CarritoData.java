package tiendaropa.dto;

import java.io.Serializable;
import java.util.Objects;

public class CarritoData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long usuarioId;  // Esta es la ID del usuario asociado

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarritoData)) return false;
        CarritoData carritoData = (CarritoData) o;
        return Objects.equals(id, carritoData.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

