package tiendaropa.service;

import tiendaropa.controller.exception.ProductoNotFoundException;
import tiendaropa.controller.exception.SinStockException;
import tiendaropa.controller.exception.UsuarioNoLogeadoException;
import tiendaropa.dto.CategoriaData;
import tiendaropa.dto.ComentarioData;
import tiendaropa.model.*;
import tiendaropa.dto.ProductoData;
import tiendaropa.repository.*;
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
public class LineaCarritoService {

    Logger logger = LoggerFactory.getLogger(ProductoService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    ProductoService productoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private LineaCarritoRepository lineaCarritoRepository;

    @Transactional
    public void añadirProductos(Carrito carrito, Long productoId, int cantidad)
            throws ProductoNotFoundException, UsuarioNoLogeadoException {
        // Verificar si el usuario está logeado
        if (carrito.getUsuario() == null) {
            throw new UsuarioNoLogeadoException();
        }

        // Obtener el producto
        Producto producto = productoRepository.findById(productoId).orElse(null);
        if (producto == null) {
            throw new ProductoNotFoundException();
        }

        // Verificar si hay suficiente stock
        if (producto.getStock() < cantidad) {
            throw new SinStockException();
        }

        // Buscar si ya existe una línea de carrito para este producto en el carrito actual
        LineaCarrito lineaExistente = carrito.getLineascarrito().stream()
                .filter(linea -> linea.getProducto().getNombre().equals(producto.getNombre()))
                .findFirst()
                .orElse(null);



        if (lineaExistente != null) {
            // Si ya existe, actualizar la cantidad
            lineaExistente.setCantidad(lineaExistente.getCantidad() + cantidad);
            logger.debug("Linea existente dentro: " + lineaExistente.getCantidad());

            lineaCarritoRepository.save(lineaExistente);
        } else {
            // Si no existe, crear una nueva línea de carrito
            LineaCarrito nuevaLinea = new LineaCarrito();
            nuevaLinea.setProducto(producto);
            nuevaLinea.setCantidad(cantidad);
            carrito.addLineascarrito(nuevaLinea);
        }

        // Actualizar el stock del producto
        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
    }
}
