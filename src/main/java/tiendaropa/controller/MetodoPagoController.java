package tiendaropa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tiendaropa.authentication.ManagerUserSession;
import tiendaropa.dto.*;
import tiendaropa.service.*;
import org.springframework.ui.Model;

@Controller
public class MetodoPagoController {
    @Autowired
    private CarritoService carritoService;
    @Autowired
    private LineaCarritoService lineaCarritoService;
    @Autowired
    private ManagerUserSession managerUserSession;
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/tiendaropa/compra/metodopago")
    public String verMetodoPago(Model model) {
        // Obtener el usuario actual (puedes modificar esto según tu lógica de autenticación)
        Long usuarioId = managerUserSession.usuarioLogeado();
        UsuarioData user = usuarioService.findById(usuarioId);

        model.addAttribute("usuario", user);

        return "checkout";
    }
}
