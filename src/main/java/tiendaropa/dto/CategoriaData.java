package tiendaropa.dto;

import java.io.Serializable;

public class CategoriaData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String nombre;
    private String descripcion;
    private Long subcategoriaid;

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
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni estar vacío");
        }
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser nula ni estar vacía");
        }
        this.descripcion = descripcion;
    }

    public Long getSubcategoriaid() {
        return subcategoriaid;
    }

    public void setSubcategoriaid(Long subcategoriaid) {
        this.subcategoriaid = subcategoriaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoriaData)) return false;
        CategoriaData categoriaData = (CategoriaData) o;
        return id.equals(categoriaData.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
