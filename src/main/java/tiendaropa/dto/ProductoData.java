package tiendaropa.dto;

import java.util.Objects;


public class ProductoData {
    private Long id;
    private String nombre;
    private float precio;
    private Integer stock;
    private String numref;
    private boolean destacado = false;
    private Long categoriaid;

    private String img;

    public String getImg() {
        return img;
    }
    public void setImg(String urlImagen) {
        this.img = urlImagen;
    }

    public ProductoData() {
    }

    public ProductoData(String nombre, float precio, Integer stock, String numref, boolean destacado, Long categoriaid) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductoData)) return false;
        ProductoData that = (ProductoData) o;
        return Float.compare(that.getPrecio(), getPrecio()) == 0 &&
                getStock() == that.getStock() &&
                getDestacado() == that.getDestacado() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getNombre(), that.getNombre()) &&
                Objects.equals(getNumref(), that.getNumref()) &&
                Objects.equals(getCategoriaid(), that.getCategoriaid());
    }
    @Override
    public int hashCode(){
        // Generamos un hash basado en los campos obligatorios
        return Objects.hash(nombre, precio, stock);
    }
}
