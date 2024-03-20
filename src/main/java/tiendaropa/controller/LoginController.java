package tiendaropa.controller;

import tiendaropa.authentication.ManagerUserSession;
import tiendaropa.dto.LoginData;
import tiendaropa.dto.RegistroData;
import tiendaropa.dto.UsuarioData;
import tiendaropa.model.Usuario;
import tiendaropa.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ManagerUserSession managerUserSession;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginData", new LoginData());
        return "formLogin";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute LoginData loginData, Model model, HttpSession session) {

        // Llamada al servicio para comprobar si el login es correcto
        UsuarioService.LoginStatus loginStatus = usuarioService.login(loginData.geteMail(), loginData.getPassword());

        if (loginStatus == UsuarioService.LoginStatus.LOGIN_OK) {
            UsuarioData usuario = usuarioService.findByEmail(loginData.geteMail());

            managerUserSession.logearUsuario(usuario.getId());

            // Si es admin se redirecciona al panel de administración
            if (usuario.isAdmin()) {
                return "redirect:/admin";
            }
            else{
                return "redirect:/home";
            }
        } else if (loginStatus == UsuarioService.LoginStatus.USER_NOT_FOUND) {
            model.addAttribute("error", "No existe usuario");
            return "formLogin";
        } else if (loginStatus == UsuarioService.LoginStatus.ERROR_PASSWORD) {
            model.addAttribute("error", "Contraseña incorrecta");
            return "formLogin";
        }
        return "formLogin";
    }

    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("registroData", new RegistroData());

        boolean admin = false;

        List<Usuario> listaUsuarios = usuarioService.listadoCompleto();

        // Buscamos en el listado completo de usuarios si hay alguno de ellos que sea admin
        for (Usuario listaUsuario : listaUsuarios) {
            if (listaUsuario.isAdmin()) {
                admin = true;
                break;
            }
        }

        model.addAttribute("admin", admin);

        return "formRegistro";
    }

   @PostMapping("/registro")
   public String registroSubmit(@Valid RegistroData registroData, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "formRegistro";
        }

        if (usuarioService.findByEmail(registroData.getEmail()) != null) {
            model.addAttribute("registroData", registroData);
            model.addAttribute("error", "El usuario " + registroData.getEmail() + " ya existe.");
            return "formRegistro";
        }

        UsuarioData usuario = new UsuarioData();
        usuario.setEmail(registroData.getEmail());
        usuario.setPassword(registroData.getPassword());
        usuario.setNombre(registroData.getNombre());

       usuario.setApellidos(registroData.getApellidos());
       usuario.setTelefono(registroData.getTelefono());
       usuario.setCodigopostal(registroData.getCodigopostal());
       usuario.setPais(registroData.getPais());
       usuario.setPoblacion(registroData.getPoblacion());
       usuario.setDireccion(registroData.getDireccion());
       usuario.setAdmin(registroData.isAdmin());

        usuarioService.registrar(usuario);
        return "redirect:/login";
   }

   @GetMapping("/logout")
   public String logout(HttpSession session) {
        managerUserSession.logout();
        return "redirect:/login";
   }

    @GetMapping("/about")
    public String about(Model model, HttpSession session) {
        // Obtenemos el id del usuario en sesión para comprobar si está logueado o no
        Long id = managerUserSession.usuarioLogeado();

        if(id != null){
            // Si está logueado, lo buscamos en la base de datos y lo añadimos al atributo "usuario"
            UsuarioData user = usuarioService.findById(id);
            // "usuario" lo usaremos en la vista html
            model.addAttribute("usuario", user);
        }

        // si no está logueado, se mostrará el navbar de no estar logueado
        return "about";
    }
}
