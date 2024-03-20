package tiendaropa.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tiendaropa.authentication.ManagerUserSession;
import tiendaropa.controller.exception.ProductoNotFoundException;
import tiendaropa.controller.exception.UsuarioNoLogeadoException;
import tiendaropa.dto.*;
import tiendaropa.model.Categoria;
import tiendaropa.model.Comentario;
import tiendaropa.model.Producto;
import tiendaropa.service.CategoriaService;
import tiendaropa.service.ComentarioService;
import tiendaropa.service.ProductoService;
import tiendaropa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Controller
public class ProductoController {

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ProductoService productoService;

    @Autowired
    CategoriaService categoriaService;

    @Autowired
    private ComentarioService comentarioService;

    private boolean comprobarUsuarioLogeado() {
        Long idUsuarioLogeado = managerUserSession.usuarioLogeado();
        if (idUsuarioLogeado == null)
            return false;

        return true;
    }

    private boolean comprobarAdmin() {
        UsuarioData usuario = usuarioService.findById(managerUserSession.usuarioLogeado());
        return usuario.isAdmin();
    }

    @GetMapping("/tiendaropa/catalogo")
    public String mostrarCatalogo(
            @RequestParam(name = "categoriaId", required = false) Long categoriaId,
            @RequestParam(name = "filtro", required = false) String filtro,
            Model model) {

        if(comprobarUsuarioLogeado()) {
            UsuarioData usuario = usuarioService.findById(managerUserSession.usuarioLogeado());
            model.addAttribute("usuario", usuario);
        } else {
            model.addAttribute("usuario", null);
        }

        List<Categoria> categorias = categoriaService.listadoCompleto();
        model.addAttribute("categorias", categorias);

        List<ProductoData> productos;

        if (filtro != null && !filtro.isEmpty()) {
            // Si se proporciona un filtro, buscar productos por ese filtro
            productos = productoService.buscarProductoPorFiltro(filtro);
        } else if (categoriaId != null) {
            // Si se proporciona una categoría, filtrar por esa categoría
            productos = productoService.buscarProductoPorCategoria(categoriaId);
        } else {
            // Si no se proporciona ninguna categoría ni filtro, mostrar todos los productos
            productos = productoService.allProductos();
        }

        model.addAttribute("productos", productos);

        return "catalogo";
    }


    @GetMapping("/tiendaropa/catalogo/busqueda")
    public String buscarProducto(
            @ModelAttribute BusquedaData busquedaData,
            @RequestParam(name = "q", required = false) String busquedaParametro,
            Model model) {

        if (comprobarUsuarioLogeado()) {
            UsuarioData usuario = usuarioService.findById(managerUserSession.usuarioLogeado());
            model.addAttribute("usuario", usuario);
        } else {
            model.addAttribute("usuario", null);
        }

        String busqueda;

        // Si se proporciona un parámetro en la URL, usa ese valor
        if (busquedaParametro != null) {
            busqueda = busquedaParametro;
        } else {
            // De lo contrario, utiliza el valor del formulario
            busqueda = busquedaData.getBusqueda();
        }

        List<ProductoData> productos = productoService.buscarProductos(busqueda);
        model.addAttribute("busquedaData", new BusquedaData());
        model.addAttribute("productos", productos);

        return "catalogo";
    }

    @GetMapping("/admin/tiendaropa/catalogo")
    public String mostrarCatalogoAdmin(Model model) {
        if(comprobarUsuarioLogeado()) {
            UsuarioData usuario = usuarioService.findById(managerUserSession.usuarioLogeado());
            model.addAttribute("usuario", usuario);
        }else{
            model.addAttribute("usuario", null);
        }
        List<Categoria> categorias = categoriaService.listadoCompleto();
        model.addAttribute("categorias", categorias);

        List<ProductoData> productos = productoService.allProductos();
        model.addAttribute("productos", productos);

        //if(comprobarAdmin()) {
            return "catalogoAdmin";
        //}
        //return "catalogo";
    }

    @DeleteMapping("/admin/tiendaropa/productos/{id}")
    @ResponseBody
    public String borrarProducto(@PathVariable(value="id") Long idProducto, RedirectAttributes flash, HttpSession session) {
        ProductoData producto = productoService.findById(idProducto);
        if (producto == null) {
            throw new ProductoNotFoundException();
        }
        if(comprobarUsuarioLogeado()){
            productoService.borrarProducto(idProducto);
        }
        return "redirect:/admin/tiendaropa/catalogo";
    }

    @GetMapping("/admin/tiendaropa/productos/{id}/editar")
    public String formEditarProducto(@PathVariable(value="id") Long idProducto, @ModelAttribute ProductoData productoData,
                                 Model model, HttpSession session) {

        ProductoData producto = productoService.findById(idProducto);
        if (producto == null) {
            throw new ProductoNotFoundException();
        }

        if(!comprobarUsuarioLogeado()) {
            throw new UsuarioNoLogeadoException();
        }
        UsuarioData usuario = usuarioService.findById(managerUserSession.usuarioLogeado());
        model.addAttribute("usuario", usuario);
        model.addAttribute("producto", producto);

        productoData.setNombre(producto.getNombre());
        productoData.setPrecio(producto.getPrecio());
        productoData.setStock(producto.getStock());
        productoData.setNumref(producto.getNumref());
        productoData.setDestacado(producto.getDestacado());
        productoData.setCategoriaid(producto.getCategoriaid());
        return "formEditarProducto";
    }

    @PostMapping("/admin/tiendaropa/productos/{id}/editar")
    public String grabaProductoModificado(@PathVariable(value = "id") Long idProducto, @ModelAttribute ProductoData productoData,
                                       Model model, RedirectAttributes flash, HttpSession session) {

        if(!comprobarUsuarioLogeado()) {
            throw new UsuarioNoLogeadoException();
        }
        UsuarioData usuario = usuarioService.findById(managerUserSession.usuarioLogeado());
        model.addAttribute("usuario", usuario);
        productoService.modificarProducto(idProducto, productoData.getNombre(), productoData.getPrecio(), productoData.getStock(), productoData.getNumref(), productoData.getDestacado(), productoData.getCategoriaid());
        flash.addFlashAttribute("mensaje", "Producto modificado correctamente");
        return "redirect:/admin/tiendaropa/catalogo";
    }

    @GetMapping("/admin/tiendaropa/productos/nuevo")
    public String formNuevoProducto(@ModelAttribute ProductoData productoData, Model model,HttpSession session){
        if(!comprobarUsuarioLogeado()){
            throw new UsuarioNoLogeadoException();
        }
        UsuarioData usuario = usuarioService.findById(managerUserSession.usuarioLogeado());
        model.addAttribute("usuario", usuario);
        return "formNuevoProducto";
    }

    @PostMapping("/admin/tiendaropa/productos/nuevo")
    public String nuevoProducto(@ModelAttribute ProductoData productoData,Model model, RedirectAttributes flash,HttpSession session){
        if(!comprobarUsuarioLogeado()){
            throw new UsuarioNoLogeadoException();
        }
        UsuarioData usuario = usuarioService.findById(managerUserSession.usuarioLogeado());
        model.addAttribute("usuario", usuario);
        productoService.crearProducto(productoData.getNombre(), productoData.getPrecio(), productoData.getStock(), productoData.getNumref(), productoData.getDestacado(), productoData.getCategoriaid());
        flash.addFlashAttribute("mensaje", "Producto creado correctamente");
        return "redirect:/admin/tiendaropa/catalogo";
    }

    @PostMapping("/tiendaropa/productos/buscarPorCategoria")
    public String buscarProductosPorCategoria(
            @RequestParam(name = "categoriaId", required = false) Long categoriaId,
            Model model) {

        // Cargar las categorías desde la base de datos
        List<Categoria> categorias = categoriaService.listadoCompleto();
        model.addAttribute("categorias", categorias);

        if (categoriaId != null) {
            // Si se selecciona una categoría, redirige a la misma página pero con el parámetro de categoría
            return "redirect:/tiendaropa/catalogo?categoriaId=" + categoriaId;
        } else {
            // Si no se selecciona ninguna categoría, mostrar todos los productos
            return "redirect:/tiendaropa/catalogo";
        }
    }

    @GetMapping("/tiendaropa/catalogo/filtro")
    public String filtrarProductosPorFiltro(
            @RequestParam(name = "filtro", required = false) String filtro,
            Model model) {

        // Cargar las categorías desde la base de datos
        List<Categoria> categorias = categoriaService.listadoCompleto();
        model.addAttribute("categorias", categorias);

        if (filtro != null && !filtro.isEmpty()) {
            // Si se proporciona un filtro, redirige a la misma página pero con el parámetro de filtro
            return "redirect:/tiendaropa/catalogo?filtro=" + filtro;
        } else {
            // Si no se proporciona un filtro válido, mostrar todos los productos
            return "redirect:/tiendaropa/catalogo";
        }
    }

    @PostMapping("/admin/tiendaropa/productos/buscarPorCategoria")
    public String buscarProductosPorCategoriaAdmin(
            @RequestParam(name = "categoriaId", required = false) Long categoriaId,
            Model model,HttpSession session) {

        if(!comprobarUsuarioLogeado()) {
            throw new UsuarioNoLogeadoException();
        }

        UsuarioData usuario = usuarioService.findById(managerUserSession.usuarioLogeado());
        model.addAttribute("usuario", usuario);

        // Cargar las categorías desde la base de datos
        List<Categoria> categorias = categoriaService.listadoCompleto();
        model.addAttribute("categorias", categorias);

        List<ProductoData> productos;

        if (categoriaId != null) {
            // Si se selecciona una categoría, filtrar por esa categoría
            productos = productoService.buscarProductoPorCategoria(categoriaId);
        } else {
            // Si no se selecciona ninguna categoría, mostrar todos los productos
            productos = productoService.allProductos();
        }

        model.addAttribute("productos", productos);

        return "catalogoAdmin";
    }


    @GetMapping("/tiendaropa/productos/{id}")
    public String mostrarDetallesProducto(@PathVariable Long id, Model model) {
        // Lógica para obtener detalles del producto por su ID
        ProductoData producto = productoService.findById(id);

        if (producto == null) {
            // Manejar el caso en el que no se encuentre el producto (puedes redirigir a una página de error)
            return "redirect:/tiendaropa/catalogo";
        }

        if(comprobarUsuarioLogeado()) {
            UsuarioData usuario = usuarioService.findById(managerUserSession.usuarioLogeado());
            model.addAttribute("usuario", usuario);
        }else{
            model.addAttribute("usuario", null);
        }

        // Agregar el producto a la vista para mostrar sus detalles
        model.addAttribute("producto", producto);
        List<ComentarioData> comentarios = productoService.obtenerComentariosPorProducto(id);
        model.addAttribute("comentarios", comentarios);

        return "detallesProducto";
    }

    @GetMapping("/admin/tiendaropa/productos/{id}")
    public String mostrarDetallesProductoAdmin(@PathVariable Long id, Model model,HttpSession session) {
        // Lógica para obtener detalles del producto por su ID
        ProductoData producto = productoService.findById(id);

        if (producto == null) {
            // Manejar el caso en el que no se encuentre el producto (puedes redirigir a una página de error)
            return "redirect:/tiendaropa/catalogo";
        }

        UsuarioData usuario = usuarioService.findById(managerUserSession.usuarioLogeado());
        model.addAttribute("usuario", usuario);

        // Agregar el producto a la vista para mostrar sus detalles
        model.addAttribute("producto", producto);
        List<ComentarioData> comentarios = productoService.obtenerComentariosPorProducto(id);
        model.addAttribute("comentarios", comentarios);

        return "detallesProductoAdmin";
    }

    @PostMapping("/tiendaropa/productos/comentar/{id}")
    public String agregarComentario(@PathVariable Long id,
                                    @RequestParam("nuevoComentario") String nuevoComentario,
                                    HttpSession session,Model model) {
        if(!comprobarUsuarioLogeado()) {
            throw new UsuarioNoLogeadoException();
        }
        UsuarioData usuario = usuarioService.findById(managerUserSession.usuarioLogeado());
        Long usuarioId = usuario.getId();

        comentarioService.agregarComentario(usuarioId, id, nuevoComentario);
        model.addAttribute("usuario", usuario);
        ProductoData producto = productoService.findById(id);
        model.addAttribute("producto", producto);
        List<ComentarioData> comentarios = productoService.obtenerComentariosPorProducto(id);
        model.addAttribute("comentarios", comentarios);

        return "redirect:/tiendaropa/productos/" + id;
    }
}
