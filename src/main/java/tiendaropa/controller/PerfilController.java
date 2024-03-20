package tiendaropa.controller;

import org.hibernate.Hibernate;
import tiendaropa.authentication.ManagerUserSession;
import tiendaropa.controller.exception.UsuarioNoLogeadoException;
import tiendaropa.dto.RegistroData;
import tiendaropa.dto.UsuarioData;
import tiendaropa.model.Pedido;
import tiendaropa.model.Usuario;
import tiendaropa.service.PedidoService;
import tiendaropa.service.UsuarioService;
import tiendaropa.service.UsuarioServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class PerfilController {
    @Autowired
    private ManagerUserSession managerUserSession;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PedidoService pedidoService;

    // Método que devuelve el perfil
    @GetMapping("/perfil/{id}")
    public String perfil(@PathVariable(value="id") Long idUsuario, Model model, HttpSession session) {
        // Obtenemos el id del usuario en sesión para comprobar si está logueado o no
        Long id = managerUserSession.usuarioLogeado();

        if(id != null){
            // Si está logueado, lo buscamos en la base de datos y lo añadimos al atributo "usuario"
            UsuarioData user = usuarioService.findById(id);
            model.addAttribute("logueado", user);

            List<Usuario> usuarios = usuarioService.listadoCompleto();
            Usuario usuario = usuarioService.buscarUsuarioPorId(usuarios, idUsuario);

            model.addAttribute("usuario", usuario);
        }
        else {
            throw new UsuarioNoLogeadoException();
        }

        return "perfil";
    }

    // Método para mostrar la página de actualización de perfil
    @GetMapping("/perfil/{id}/actualizar")
    public String mostrarActualizarPerfil(@PathVariable(value="id") Long idUsuario, Model model, HttpSession session) {
        Long id = managerUserSession.usuarioLogeado();

        if(id != null){
            List<Usuario> usuarios = usuarioService.listadoCompleto();
            Usuario usuario = usuarioService.buscarUsuarioPorId(usuarios, idUsuario);
            model.addAttribute("usuario", usuario);

            RegistroData nuevo = new RegistroData();
            nuevo.setEmail(usuario.getEmail());
            nuevo.setPassword(usuario.getPassword());
            nuevo.setNombre(usuario.getNombre());
            nuevo.setApellidos(usuario.getApellidos());
            nuevo.setTelefono(usuario.getTelefono());
            nuevo.setCodigopostal(usuario.getCodigopostal());
            nuevo.setPais(usuario.getPais());
            nuevo.setPoblacion(usuario.getPoblacion());
            nuevo.setDireccion(usuario.getDireccion());

            model.addAttribute("registroData", nuevo);
        }
        else {
            throw new UsuarioNoLogeadoException();
        }

        return "actualizarPerfil";
    }

    // Método para manejar la actualización del perfil
    @PostMapping("/perfil/{id}/actualizar")
    public String actualizarPerfil(@PathVariable(value="id") Long idUsuario, @Valid RegistroData registroData, BindingResult result, Model model, HttpSession session) {
        Long id = managerUserSession.usuarioLogeado();

        if (result.hasErrors()) {
            System.out.println("Ha ocurrido un error.");
        }
        else{
            if(id != null){
                try {
                    UsuarioData nuevoUsuarioData = usuarioService.findById(idUsuario);

                    if(registroData.getEmail() != null && registroData.getPassword() != null && registroData.getNombre() != null) {
                        nuevoUsuarioData.setEmail(registroData.getEmail());
                        nuevoUsuarioData.setPassword(registroData.getPassword());
                        nuevoUsuarioData.setNombre(registroData.getNombre());
                        nuevoUsuarioData.setApellidos(registroData.getApellidos());
                        nuevoUsuarioData.setTelefono(registroData.getTelefono());
                        nuevoUsuarioData.setPais(registroData.getPais());
                        nuevoUsuarioData.setPoblacion(registroData.getPoblacion());
                        nuevoUsuarioData.setDireccion(registroData.getDireccion());
                        nuevoUsuarioData.setCodigopostal(registroData.getCodigopostal());

                        // Validar y actualizar los datos del usuario en el servicio
                        usuarioService.actualizarUsuarioPorId(idUsuario, nuevoUsuarioData);

                        // Redirigir al perfil del usuario
                        return "redirect:/perfil/" + idUsuario;
                    }
                    else{
                        model.addAttribute("errorActualizar", "Ninguno de los campos puede estar vacio.");
                    }

                } catch (UsuarioServiceException e) {
                    model.addAttribute("errorActualizar", e.getMessage());
                }
            }
            else {
                throw new UsuarioNoLogeadoException();
            }
        }

        model.addAttribute("registroData", registroData);

        UsuarioData user = usuarioService.findById(id);
        model.addAttribute("logueado", user);

        List<Usuario> usuarios = usuarioService.listadoCompleto();
        Usuario usuario = usuarioService.buscarUsuarioPorId(usuarios, idUsuario);

        model.addAttribute("usuario", usuario);

        return "actualizarPerfil";
    }

    @GetMapping("/perfil/{id}/pedidos")
    public String mostrarPedidosUsuario(@PathVariable(value="id") Long idUsuario, Model model, HttpSession session) {
        Long id = managerUserSession.usuarioLogeado();

        if(id != null){
            List<Usuario> usuarios = usuarioService.listadoCompleto();
            Usuario usuario = usuarioService.buscarUsuarioPorId(usuarios, idUsuario);
            model.addAttribute("usuario", usuario);
            model.addAttribute("pedidos", usuario.getPedidos());
        }
        else {
            throw new UsuarioNoLogeadoException();
        }

        return "pedidosUsuario";
    }

    @GetMapping("/perfil/{id}/pedidos/{pedidoId}")
    public String mostrarLineasPedidoUsuario(@PathVariable(value="id") Long idUsuario, @PathVariable(value="pedidoId") Long pedidoId, Model model, HttpSession session) {
        Long id = managerUserSession.usuarioLogeado();

        if(id != null){
            List<Usuario> usuarios = usuarioService.listadoCompleto();
            Usuario usuario = usuarioService.buscarUsuarioPorId(usuarios, idUsuario);
            model.addAttribute("usuario", usuario);

            Set<Pedido> pedidos = usuario.getPedidos();
            List<Pedido> listaPedidos = new ArrayList<>(pedidos);

            Pedido pedido = pedidoService.buscarPedidoPorId(listaPedidos, pedidoId);
            model.addAttribute("lineas", pedido.getLineaspedido());
        }
        else {
            throw new UsuarioNoLogeadoException();
        }

        return "lineasPedidoUsuario";
    }
}
