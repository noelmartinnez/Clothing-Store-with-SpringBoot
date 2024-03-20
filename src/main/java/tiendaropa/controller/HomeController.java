package tiendaropa.controller;

import tiendaropa.authentication.ManagerUserSession;
import tiendaropa.dto.UsuarioData;
import tiendaropa.model.Producto;
import tiendaropa.service.ProductoService;
import tiendaropa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ManagerUserSession managerUserSession;

    @Autowired
    ProductoService productoService;

    @GetMapping("/")
    public String init(Model model) {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        Long id = managerUserSession.usuarioLogeado();

        if(id != null){
            UsuarioData user = usuarioService.findById(id);
            model.addAttribute("usuario", user);
        }
        List<Producto> productosDestacados = productoService.obtenerProductosDestacados();
        model.addAttribute("productosDestacados", productosDestacados);
        return "home";
    }
}
