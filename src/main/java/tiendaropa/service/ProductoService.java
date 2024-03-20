package tiendaropa.service;

import tiendaropa.dto.CategoriaData;
import tiendaropa.dto.ComentarioData;
import tiendaropa.model.Comentario;
import tiendaropa.model.Producto;
import tiendaropa.dto.ProductoData;
import tiendaropa.model.Usuario;
import tiendaropa.repository.ComentarioRepository;
import tiendaropa.repository.ProductoRepository;
import tiendaropa.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ProductoService {

    Logger logger = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<ProductoData> allProductos() {
        logger.debug("Devolviendo todos los productos");
        //Usuario usuario = usuarioRepository.findById(idUsuario).orElse(null);

        List<Producto> productos = productoRepository.findAll();
        List<ProductoData> productosDTO = productos.stream().map(producto -> modelMapper.map(producto, ProductoData.class)).collect(Collectors.toList());

        return productosDTO;
    }

    @Transactional(readOnly = true)
    public List<ProductoData> buscarProductos(String busqueda) {
        logger.debug("Devolviendo todos los productos con busqueda");

        List<ProductoData> productos = allProductos();
        List<ProductoData> filtrados;
        filtrados = productos.stream().filter(producto -> producto.getNombre().contains(busqueda)).collect(Collectors.toList());

        return filtrados;
    }

    @Transactional(readOnly = true)
    public ProductoData findById(Long productoId) {
        logger.debug("Buscando producto " + productoId);
        Producto tarea = productoRepository.findById(productoId).orElse(null);
        if (tarea == null) return null;
        else return modelMapper.map(tarea, ProductoData.class);
    }

    @Transactional
    public void borrarProducto(Long idProducto) {
        logger.debug("Borrando producto " + idProducto);
        Producto producto = productoRepository.findById(idProducto).orElse(null);
        if (producto == null) {
            //throw new TareaServiceException("No existe tarea con id " + idTarea);
        }else{
            productoRepository.delete(producto);
        }
        //descomentar esta linea cuando ponga excepcion en if, ademas quitar else
        //productoRepository.delete(producto);

    }

    @Transactional
    public ProductoData modificarProducto(Long idProducto, String nombre, float precio, Integer stock, String numref, boolean destacado, Long categoriaid){
        logger.debug("Modificando producto " + idProducto + " - " + nombre);
        Producto producto = productoRepository.findById(idProducto).orElse(null);
        if (producto == null) {
            //throw new TareaServiceException("No existe tarea con id " + idTarea);
        }

        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setNumref(numref);
        producto.setDestacado(destacado);
        producto.setCategoriaid(categoriaid);

        producto = productoRepository.save(producto);
        return modelMapper.map(producto, ProductoData.class);
    }

    @Transactional
    public ProductoData crearProducto(String nombre, float precio, Integer stock, String numref, boolean destacado, Long categoriaid){
        logger.debug("Creando producto " + nombre);
        Producto producto = new Producto(nombre, precio, stock, numref, destacado, categoriaid);
        productoRepository.save(producto);
        return modelMapper.map(producto, ProductoData.class);
    }

    @Transactional
    public List<Producto> obtenerProductosDestacados() {
        return productoRepository.findByDestacadoIsTrue(); // Suponiendo que tienes un campo 'destacado' en tu entidad Producto
    }

    @Transactional
    public List<ProductoData> buscarProductoPorCategoria(Long categoriaId) {
        if (categoriaId == null) {
            throw new IllegalArgumentException("La categoría no puede ser nula");
        }

        List<ProductoData> productos = allProductos();
        // Filtrar productos por la categoría proporcionada
        List<ProductoData> filtrados = productos.stream()
                .filter(producto -> categoriaId.equals(producto.getCategoriaid()))
                .collect(Collectors.toList());

        return filtrados;
    }

    @Transactional(readOnly = true)
    public List<ComentarioData> obtenerComentariosPorProducto(Long productoId) {
        logger.debug("Obteniendo comentarios para el producto " + productoId);

        Producto producto = productoRepository.findById(productoId).orElse(null);

        if (producto == null) {
            logger.error("Producto no encontrado con ID: " + productoId);
            return Collections.emptyList();
        }

        Set<Comentario> comentarios = producto.getComentarios();

        // Mapear los comentarios a DTO si es necesario
        List<ComentarioData> comentariosDTO = comentarios.stream()
                .map(comentario -> modelMapper.map(comentario, ComentarioData.class))
                .collect(Collectors.toList());

        return comentariosDTO;
    }

    @Transactional(readOnly = true)
    public List<ProductoData> buscarProductoPorFiltro(String nombreCategoria) {
        logger.debug("Buscando productos por nombre de categoría: " + nombreCategoria);

        // Obtener el ID de la categoría basado en el nombre (puedes implementar este método en tu servicio de categoría)
        Long idCategoria = categoriaService.obtenerIdPorNombre(nombreCategoria);

        if (idCategoria != null) {
            List<ProductoData> productos = allProductos();

            // Filtrar productos por la categoría proporcionada
            List<ProductoData> filtrados = productos.stream()
                    .filter(producto -> idCategoria.equals(producto.getCategoriaid()))
                    .collect(Collectors.toList());

            return filtrados;
        } else {
            logger.warn("No se encontró ninguna categoría con el nombre: " + nombreCategoria);
            return Collections.emptyList();
        }
    }

}
